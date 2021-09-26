package com.sameershelar.shaadiassignment.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sameershelar.shaadiassignment.R
import com.sameershelar.shaadiassignment.data.model.db.Member
import com.sameershelar.shaadiassignment.databinding.ShaadiMemberItemLayoutBinding

class MembersAdapter(
    private val onMemberAcceptedOrDeclinedClickListener: OnMemberAcceptedOrDeclinedClickListener
) : RecyclerView.Adapter<MembersAdapter.MembersViewHolder>() {

    //region declarations and variables

    private var membersList: List<Member> = ArrayList()

    //endregion

    //region override methods

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MembersViewHolder {
        val binding = ShaadiMemberItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MembersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MembersViewHolder, position: Int) {
        val currentMember = membersList[position]

        val name =
            "${currentMember.name.title}. ${currentMember.name.first} ${currentMember.name.last}"
        val address =
            "${currentMember.location.city}, ${currentMember.location.state}, ${currentMember.location.country}"
        val age = "${currentMember.dob.age} Years old"
        val profileImageUrl = currentMember.picture.large

        holder.binding.nameText.text = name
        holder.binding.addressText.text = address
        holder.binding.ageText.text = age

        //Loading image using Glide.
        Glide.with(holder.binding.root.context)
            .load(profileImageUrl)
            .placeholder(R.drawable.ic_profile_place_holder)
            .error(R.drawable.ic_profile_place_holder)
            .centerCrop()
            .into(holder.binding.profileImage)

        //If selection is done show the selection text else show buttons.
        if (currentMember.memberSelection != null) {
            holder.binding.selectionButtonsLayout.isVisible = false
            holder.binding.userSelectionCard.isVisible = true

            currentMember.memberSelection?.let {
                if (it.isAccepted) {
                    holder.binding.userSelectionCard.setBackgroundColor(
                        ContextCompat.getColor(
                            holder.binding.root.context,
                            R.color.shaadi_red_light
                        )
                    )
                    holder.binding.userSelectionText.setTextColor(
                        ContextCompat.getColor(
                            holder.binding.root.context,
                            R.color.white
                        )
                    )
                    holder.binding.userSelectionText.setText(R.string.you_have_accepted_this_member)
                } else {
                    holder.binding.userSelectionCard.setBackgroundColor(
                        ContextCompat.getColor(
                            holder.binding.root.context,
                            R.color.grey
                        )
                    )
                    holder.binding.userSelectionText.setTextColor(
                        ContextCompat.getColor(
                            holder.binding.root.context,
                            R.color.text_grey
                        )
                    )
                    holder.binding.userSelectionText.setText(R.string.you_have_declined_this_member)
                }
            }
        } else {
            holder.binding.selectionButtonsLayout.isVisible = true
            holder.binding.userSelectionCard.isVisible = false

            holder.binding.acceptButton.setOnClickListener {
                onMemberAcceptedOrDeclinedClickListener.onMemberAcceptedOrDeclinedClicked(
                    currentMember,
                    position,
                    true
                )
            }
            holder.binding.declineButton.setOnClickListener {
                onMemberAcceptedOrDeclinedClickListener.onMemberAcceptedOrDeclinedClicked(
                    currentMember,
                    position,
                    false
                )
            }
        }
    }

    override fun getItemCount(): Int = membersList.size

    //endregion

    //region getter and setter methods

    fun submitList(newMemberList: List<Member>) {
        val oldList = membersList
        val diffResult = DiffUtil.calculateDiff(
            MemberItemDiffCallback(
                oldList,
                newMemberList,
            )
        )
        membersList = newMemberList

        diffResult.dispatchUpdatesTo(this)
    }

    //endregion

    //region callbacks

    class MemberItemDiffCallback(
        private val oldList: List<Member>,
        private val newList: List<Member>,
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].mId == newList[newItemPosition].mId

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] == newList[newItemPosition]
    }

    //endregion

    inner class MembersViewHolder(val binding: ShaadiMemberItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}

//region callback interfaces

interface OnMemberAcceptedOrDeclinedClickListener {
    fun onMemberAcceptedOrDeclinedClicked(member: Member, position: Int, isAccepted: Boolean)
}

//endregion