import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import tn.sesame.designsystem.components.SesameFontFamilies
import tn.sesame.designsystem.onBackgroundShadedLightMode

@Composable
fun SesameTextField(
    text: String,
    label: String,
    placeholder: String,
    isEnabled: Boolean,
    isReadOnly: Boolean,
    isError: Boolean,
    visualTransformation : VisualTransformation = VisualTransformation.None,
    leftIconRes : Int?=null,
    rightIconRes : Int?=null,
    onLeftIconResClicked : (()->Unit)?=null,
    onRightIconResClicked : (()->Unit)?=null,
    onTextChanged: (text: String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        label = {
            Text(
                text = label,
                style = TextStyle(
                    color = onBackgroundShadedLightMode,
                    fontSize = 12.sp,
                    fontFamily = SesameFontFamilies.MainMediumFontFamily,
                    textAlign = TextAlign.Start
                )
            )
        },
        placeholder = {
            PlaceholderText(
                text = placeholder,
                fontSize = 14.sp
            )
        },
        enabled = isEnabled,
        readOnly = isReadOnly,
        isError = isError,
        singleLine = true,
        leadingIcon = leftIconRes?.run{
            {
                Icon(
                    modifier = Modifier.clickable {
                        onLeftIconResClicked?.let { callback->
                            callback()
                        }
                    },
                    imageVector = ImageVector.vectorResource(this),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        },
        trailingIcon = rightIconRes?.run{
            {
                Icon(
                    modifier = Modifier.clickable {
                        onRightIconResClicked?.let { callback->
                            callback()
                        }
                    },
                    imageVector = ImageVector.vectorResource(this),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        },
        visualTransformation = visualTransformation,
        colors = OutlinedTextFieldDefaults.colors(
           focusedContainerColor = Color.Unspecified,
           cursorColor = MaterialTheme.colorScheme.secondary,
            focusedLabelColor = MaterialTheme.colorScheme.secondary,
            focusedTrailingIconColor = Color.Unspecified,
            unfocusedContainerColor = Color.Unspecified,
            focusedBorderColor = MaterialTheme.colorScheme.secondary
        ),
        onValueChange = onTextChanged
    )

}