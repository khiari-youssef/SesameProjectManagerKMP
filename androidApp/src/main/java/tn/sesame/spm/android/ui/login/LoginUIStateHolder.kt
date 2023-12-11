package tn.sesame.spm.android.ui.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.remember

sealed interface LoginState {
  data object Idle : LoginState
  data object Loading : LoginState
  sealed interface LoginResult : LoginState{
      data class Error(val code : Int) : LoginResult
      data class Success(val user : Any) : LoginResult
      data object RequireBiometricAuth : LoginResult
  }
}

data class LoginUIStateHolder(
  val loginEmail : MutableState<String>,
  val loginPassword : MutableState<String>,
  val isAutoLoginEnabled : State<Boolean>,
  val loginRequestResult : State<LoginState>
){
    companion object{
        @Composable
        fun rememberLoginUIState(
             loginEmail : MutableState<String>,
             loginPassword : MutableState<String>,
             isAutoLoginEnabled : State<Boolean>,
             loginRequestResult : State<LoginState>
        ) : LoginUIStateHolder = remember(loginEmail, loginPassword, isAutoLoginEnabled, loginRequestResult) {
            LoginUIStateHolder(
              loginEmail, loginPassword, isAutoLoginEnabled, loginRequestResult
            )
        }
    }
}