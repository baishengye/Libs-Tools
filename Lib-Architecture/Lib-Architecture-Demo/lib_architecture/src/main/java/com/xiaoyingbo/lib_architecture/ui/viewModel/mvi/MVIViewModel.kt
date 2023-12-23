package com.xiaoyingbo.lib_architecture.ui.viewModel.mvi

import androidx.lifecycle.viewModelScope
import com.xiaoyingbo.lib_architecture.ui.viewModel.LifecycleViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception

abstract class MVIViewModel<IA : MVIInterface.IAction, MS : MVIInterface.MState, ME : MVIInterface.MEffect> :
    LifecycleViewModel() {

    /*region State=======================================================================================================*/
    private val _state by lazy { MutableStateFlow(value = initialState()) }
    val state: StateFlow<MS> by lazy { _state.asStateFlow() }
    abstract fun initialState(): MS
    protected fun updateState(copy: suspend MS.() -> MS) = viewModelScope.launch {
        _state.update { copy.invoke(_state.value) }
    }

    /**suspend 函数在flow或者scope中emit状态*/
    protected suspend fun emitState(state: MS) = _state.emit(state)
    /*endregion */

    /*region Effect======================================================================================================*/
    //通常是一次性事件 例如：弹Toast、导航Fragment等
    private val _effect = MutableSharedFlow<ME>()
    val effect: SharedFlow<ME> by lazy { _effect.asSharedFlow() }

    protected fun updateEffect(builder: suspend () -> ME) = viewModelScope.launch {
        builder().let { _effect.emit(it) }
    }

    protected suspend fun emitEffect(effect: ME) = _effect.emit(effect)
    /*endregion*/

    /*region Action======================================================================================================*/
    private val _action = Channel<IA>(Channel.UNLIMITED)
    /** [action] 用于在非viewModelScope外使用*/
    val action: SendChannel<IA> by lazy { _action }
    init {
        viewModelScope.launch {
            _action.consumeAsFlow().collect {
                /*replayState：很多时候我们需要通过上个state的数据来处理这次数据，所以我们要获取当前状态传递*/
                handleAction(it, _state.value)
            }
        }
    }
    fun sendAction(action: IA) = viewModelScope.launch {
        _action.send(action)
    }

    /** 订阅事件的传入 onAction()分发处理事件 */
    protected abstract fun handleAction(action: IA, currentState: MS?)
    /*endregion*/

    //flow异步请求
    protected fun <T>requestFlow(
        request:suspend (FlowCollector<T>)->Unit,
        requestStart:suspend (FlowCollector<T>)->Unit = {},
        requestEmpty: suspend (FlowCollector<T>) -> Unit = {},
        requestEach: suspend (T) -> Unit = {},
        requestCompletion:suspend (FlowCollector<T>,Throwable?)->Unit = {_,_->},
        requestException: suspend (FlowCollector<T>,Throwable?) -> Unit = {_,_->}
    ):Flow<T> {
        val flow = flow {
            request.invoke(this)
        }
        flow.flowOn(Dispatchers.IO)
            .onStart {
                requestStart.invoke(this)
            }
            .onEmpty {
                requestEmpty.invoke(this)
            }
            .onEach {
                requestEach.invoke(it)
            }
            .onCompletion {
                requestCompletion.invoke(this,it)
            }
            .catch {
                requestException.invoke(this,it)
            }
            .launchIn(viewModelScope)
        return flow
    }
}