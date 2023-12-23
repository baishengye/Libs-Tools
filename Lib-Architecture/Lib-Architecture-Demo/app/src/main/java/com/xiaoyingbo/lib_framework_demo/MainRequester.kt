package com.xiaoyingbo.lib_framework_demo

import androidx.lifecycle.viewModelScope
import com.xiaoyingbo.lib_architecture.ui.viewModel.mvi.MVIInterface
import com.xiaoyingbo.lib_architecture.ui.viewModel.mvi.MVIViewModel
import com.xiaoyingbo.lib_framework_demo.network.FemaleApi
import com.xiaoyingbo.lib_framework_demo.network.HttpUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainRequester :
    MVIViewModel<MainRequester.MainIAction, MainRequester.MainMState, MainRequester.MainMEffect>() {
    sealed class MainIAction : MVIInterface.IAction {
        object GetFemaleIAction : MainIAction()
    }

    sealed class MainMState : MVIInterface.MState {
        data class FemaleMState(val female: FemaleApi.Female?) : MainMState()

        data class LoadingMState(val isLoading: Boolean) : MainMState()
    }

    sealed class MainMEffect : MVIInterface.MEffect {
        data class ToastMState(val msg: String) : MainMEffect()
    }

    override fun initialState(): MainMState {
        return MainMState.FemaleMState(null)
    }

    override fun handleAction(action: MainIAction, currentState: MainMState?) {
        when (action) {
            is MainIAction.GetFemaleIAction -> {
                requestFlow<MainMState>(
                    request = {
                        it.emit(MainMState.FemaleMState(HttpUtil.instance.getFemale(this@MainRequester)))
                    },
                    requestStart = {
                        emitState(MainMState.LoadingMState(true))
                    },
                    requestEach = {
                        emitState(MainMState.LoadingMState(false))
                        emitState(it)
                        emitEffect(MainMEffect.ToastMState("请求成功"))
                    },
                    requestCompletion = { _, _ ->

                    },
                    requestEmpty = {

                    },
                    requestException = { _, _ ->
                        emitState(MainMState.LoadingMState(false))
                        emitEffect(MainMEffect.ToastMState("失败"))
                    }
                )

            }
        }
    }

}