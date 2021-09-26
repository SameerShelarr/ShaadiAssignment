package com.sameershelar.shaadiassignment.events

sealed class ShaadiAPIEvents {
    object ShaadiAPIDataLoadingStarted : ShaadiAPIEvents()
    object ShaadiAPIDataLoadingFinished : ShaadiAPIEvents()
    object ShaadiAPIDataFetchFailed : ShaadiAPIEvents()
}
