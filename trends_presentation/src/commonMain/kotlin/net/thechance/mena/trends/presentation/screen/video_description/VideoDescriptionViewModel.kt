package net.thechance.mena.trends.presentation.screen.video_description

import net.thechance.mena.trends.presentation.shared.base.BaseViewModel
import net.thechance.mena.trends.presentation.shared.util.ArgsConstant.TREND_ID
import net.thechance.mena.trends.presentation.shared.util.save_state.CustomSaveState
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Provided

@KoinViewModel
internal class VideoDescriptionViewModel(
    @Provided customSaveStateHandleHandle: CustomSaveState,
) : BaseViewModel<VideoDescriptionState, VideoDescriptionEffect>(initialState = VideoDescriptionState()),
    VideoDescriptionInteractionListener {

    val trendId = customSaveStateHandleHandle.get<String>(TREND_ID).toString()

    override fun onBackClick() {
        sendEffect(VideoDescriptionEffect.NavigateBack)
    }

    override fun onNextClick() {
        sendEffect(VideoDescriptionEffect.NavigateToSelectCategories(state.value.description, trendId))
    }

    override fun onDescriptionChanged(newValue: String) {
        updateState {
            copy(
                description = newValue,
                currentNumberOfCharacters = newValue.length,
                isButtonEnabled = newValue.length <= maxNumberOfCharacters
            )
        }
    }
}