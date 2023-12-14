package com.xiaoyingbo.lib_architecture.ui.viewModel;

import androidx.lifecycle.ViewModel;

/**基于 "单一职责原则"，应将 ViewModel 划分为 state-ViewModel 和 event-ViewModel,<br></br>
 state-ViewModel 职责仅限于托管、保存和恢复本页面 state，<br></br>
 event-ViewModel 职责仅限于 "消息分发" 场景承担 "唯一可信源"。<br></br>
 StateHolder用于持有state*/
public class StateHolder extends ViewModel {
}
