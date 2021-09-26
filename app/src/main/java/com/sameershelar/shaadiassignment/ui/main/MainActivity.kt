package com.sameershelar.shaadiassignment.ui.main

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.sameershelar.shaadiassignment.R
import com.sameershelar.shaadiassignment.data.model.db.Member
import com.sameershelar.shaadiassignment.data.model.other.MemberSelection
import com.sameershelar.shaadiassignment.databinding.ActivityMainBinding
import com.sameershelar.shaadiassignment.events.ShaadiAPIEvents
import com.sameershelar.shaadiassignment.utils.AppConstants
import com.sameershelar.shaadiassignment.utils.exhaustive
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnMemberAcceptedOrDeclinedClickListener {

    //region declarations and variables

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MembersAdapter
    private val viewModel: MainViewModel by viewModels()

    //endregion

    //region override methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        findViewById<Toolbar>(R.id.tool_bar).also {
            setSupportActionBar(it)
        }

        adapter = MembersAdapter(this)
        adapter.stateRestorationPolicy = PREVENT_WHEN_EMPTY

        binding.apply {
            progressBar.isVisible = true
            shaadiUserRecyclerView.layoutManager =
                LinearLayoutManager(this@MainActivity)
            shaadiUserRecyclerView.adapter = adapter

            retryButton.setOnClickListener {
                viewModel.getMembersFromRemoteServerAndSaveInDb()
            }
        }

        viewModel.getMembersFromRemoteServerAndSaveInDb()

        viewModel.getAllMembers().observe(this) { members ->
            Log.d(TAG, "onCreate: Found ${members.size} members in DB")
            if (members.isNotEmpty()) {
                adapter.submitList(members)
            } else {
                binding.emptyView.isVisible = true
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.shaadiAPIEvents.collect { event ->
                when (event) {
                    is ShaadiAPIEvents.ShaadiAPIDataLoadingStarted -> {
                        binding.progressBar.isVisible = true
                        binding.emptyView.isVisible = false
                    }

                    is ShaadiAPIEvents.ShaadiAPIDataLoadingFinished -> {
                        binding.progressBar.isVisible = false
                        binding.emptyView.isVisible = false
                    }

                    is ShaadiAPIEvents.ShaadiAPIDataFetchFailed -> {
                        Snackbar.make(
                            binding.root,
                            R.string.unable_to_fetch_members,
                            Snackbar.LENGTH_LONG
                        ).setAction(R.string.retry) {
                            viewModel.getMembersFromRemoteServerAndSaveInDb()
                        }.show()

                        lifecycleScope.launch {
                            binding.emptyView.isVisible = viewModel.isMembersTableEmpty()
                        }
                        binding.progressBar.isVisible = false
                    }
                }.exhaustive
            }
        }
    }

    override fun onMemberAcceptedOrDeclinedClicked(
        member: Member,
        position: Int,
        isAccepted: Boolean
    ) {
        val memberSelection = MemberSelection(Calendar.getInstance().time, isAccepted)
        member.memberSelection = memberSelection
        viewModel.updateMember(member)
    }

    //endregion

    //region helper methods

    //For testing
    private fun addDummyData() {
        val rootJsonObject = Gson().fromJson(AppConstants.jsonString, JsonObject::class.java)
        val membersJsonArray = rootJsonObject.getAsJsonArray("results")

        val membersList = mutableListOf<Member>()

        membersJsonArray.forEach { jsonElement ->
            membersList.add(Gson().fromJson(jsonElement, Member::class.java))
        }

        membersList.forEach { member ->
            viewModel.insertMember(member)
        }
    }

    //endregion
}