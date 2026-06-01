package com.naji.cashtracker.feature.auth.presentation.register

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import android.widget.Toast
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.naji.cashtracker.R
import com.naji.cashtracker.core.presentation.ObserveAsEvents
import com.naji.cashtracker.core.presentation.UiText
import com.naji.cashtracker.core.presentation.components.CashTrackerPrimaryButton
import com.naji.cashtracker.core.presentation.components.CashTrackerTextField
import com.naji.cashtracker.core.presentation.components.FormField
import com.naji.cashtracker.ui.theme.CashTrackerTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterRoot(
    onNavigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            viewModel.onAction(RegisterAction.OnPhotoPicked(uri.toString()))
        }
    }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            RegisterEvent.NavigateToHome -> onNavigateToHome()
            is RegisterEvent.ShowToast -> {
                val message = when (val msg = event.message) {
                    is UiText.DynamicString -> msg.value
                    is UiText.StringResource -> context.getString(msg.id, *msg.args)
                }
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    RegisterScreen(
        state = state,
        onAction = viewModel::onAction,
        onPickPhoto = {
            photoPickerLauncher.launch(
                PickVisualMediaRequest(
                    ActivityResultContracts.PickVisualMedia.ImageOnly
                )
            )
        },
        modifier = modifier
    )
}

@Composable
fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit,
    onPickPhoto: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 400.dp)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.register_title),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(R.string.register_subtitle),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            AvatarSection(
                photoUri = state.photoUri,
                onPickPhoto = onPickPhoto
            )

            Spacer(modifier = Modifier.height(20.dp))

            FormField(
                label = stringResource(R.string.register_name_label),
                value = state.name,
                onValueChange = { onAction(RegisterAction.OnNameChange(it)) },
                placeholder = stringResource(R.string.register_name_placeholder),
                isError = state.nameError != null,
                errorMessage = (state.nameError as? com.naji.cashtracker.core.presentation.UiText.DynamicString)?.value,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )

            Spacer(modifier = Modifier.height(14.dp))

            FormField(
                label = stringResource(R.string.register_email_label),
                value = state.email,
                onValueChange = { onAction(RegisterAction.OnEmailChange(it)) },
                placeholder = stringResource(R.string.register_email_placeholder),
                isError = state.emailError != null,
                errorMessage = (state.emailError as? com.naji.cashtracker.core.presentation.UiText.DynamicString)?.value,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )

            Spacer(modifier = Modifier.height(14.dp))

            FormField(
                label = stringResource(R.string.register_phone_label),
                value = state.phone,
                onValueChange = { onAction(RegisterAction.OnPhoneChange(it)) },
                placeholder = stringResource(R.string.register_phone_placeholder),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                )
            )

            Spacer(modifier = Modifier.height(14.dp))

            BioField(
                value = state.bio,
                onValueChange = { onAction(RegisterAction.OnBioChange(it)) }
            )

            Spacer(modifier = Modifier.height(20.dp))

            CashTrackerPrimaryButton(
                text = stringResource(R.string.register_button),
                onClick = { onAction(RegisterAction.OnRegister) },
                enabled = !state.isLoading
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun AvatarSection(
    photoUri: String?,
    onPickPhoto: () -> Unit,
    modifier: Modifier = Modifier
) {
    val outlineColor = MaterialTheme.colorScheme.outline

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .clickable(onClick = onPickPhoto)
                .background(MaterialTheme.colorScheme.surface)
                .drawBehind {
                    drawCircle(
                        color = outlineColor,
                        style = Stroke(
                            width = 2.dp.toPx(),
                            pathEffect = PathEffect.dashPathEffect(
                                floatArrayOf(10f, 5f), 0f
                            )
                        ),
                        radius = size.minDimension / 2 - 1.dp.toPx()
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            if (photoUri != null) {
                AsyncImage(
                    model = photoUri,
                    contentDescription = stringResource(R.string.register_add_photo),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Text(
                    text = "+",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Light,
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = stringResource(R.string.register_add_photo),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun BioField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor = MaterialTheme.colorScheme.outline

    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.register_bio_label),
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.3.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(5.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .clip(MaterialTheme.shapes.small)
                .border(
                    width = 1.5.dp,
                    color = borderColor,
                    shape = MaterialTheme.shapes.small
                )
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 14.dp, vertical = 12.dp)
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxSize(),
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) {
                        Text(
                            text = stringResource(R.string.register_bio_placeholder),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                        )
                    }
                    innerTextField()
                }
            )
        }
    }
}

@Preview(/*locale = "ar", uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL*/)
@Composable
private fun RegisterScreenPreview() {
    CashTrackerTheme {
        RegisterScreen(
            state = RegisterState(
                /*name = "Alex Chen",
                email = "alex@email.com",
                phone = "+970 59 123 4567",
                bio = "Track your expenses and achieve your financial goals."*/
            ),
            onAction = {},
            onPickPhoto = {}
        )
    }
}
