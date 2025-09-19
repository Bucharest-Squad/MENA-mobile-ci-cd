package net.thechance.mena.dukan.presentation.viewModel.createDukan

import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import app.cash.turbine.test
import dev.mokkery.MockMode
import dev.mokkery.answering.calls
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import io.github.dellisd.spatialk.geojson.Position
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import net.thechance.mena.dukan.domain.entity.Category
import net.thechance.mena.dukan.domain.entity.Color
import net.thechance.mena.dukan.domain.entity.Dukan
import net.thechance.mena.dukan.domain.repository.DukanRepository
import net.thechance.mena.dukan.domain.repository.LocationRepository
import org.maplibre.compose.camera.CameraPosition
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class CreateDukanViewModelTest {

    private val locationRepository = mock<LocationRepository>(mode = MockMode.autofill)
    private val dukanRepository = mock<DukanRepository>(mode = MockMode.autofill)
    private lateinit var createDukanViewModel: CreateDukanViewModel
    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        everySuspend { dukanRepository.getDukanStyles() } returns fakeDukanStyle()
        everySuspend { dukanRepository.getDukanColors() } returns fakeDukanColor()
        everySuspend { dukanRepository.getCategories() } returns fakeCategories()

        createDukanViewModel = CreateDukanViewModel(
            dukanRepository,
            locationRepository,
            testDispatcher
        )
    }

    @Test
    fun `init should load categories`() = runTest {
        // When
        createDukanViewModel.state.test {
            val state = awaitItem()
            // Then
            assertEquals(fakeCategories().size, state.dukanCategories.size)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `init should load colors`() = runTest {
        // When
        createDukanViewModel.state.test {
            val state = awaitItem()
            // Then
            assertEquals(fakeDukanColor().size, state.dukanColors.size)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `init should load styles`() = runTest {
        // When
        createDukanViewModel.state.test {
            val state = awaitItem()
            // Then
            assertEquals(fakeDukanStyle().size, state.dukanStyles.size)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onAddressChanged SHOULD update address state`() = runTest {
        // Given
        val address = "This new address"

        // When
        createDukanViewModel.onAddressChanged(address)

        // Then
        val newAddress = createDukanViewModel.state.value.address
        assertEquals(newAddress, address)
    }

    @Test
    fun `onAddressChanged SHOULD set isButtonEnabled state to false if address is blank`() =
        runTest {
            // Given
            val address = ""

            // When
            createDukanViewModel.onAddressChanged(address)

            // Then
            val buttonState = createDukanViewModel.state.value.isButtonEnabled
            assertFalse(buttonState)
        }

    @Test
    fun `onMapClicked SHOULD return the address of the selected location`() = runTest {
        // Given
        val expectedAddress = "Egypt"
        val selectedCoordinates = CreateDukanUiState.CoordinatesUiState(28.0, 29.0)
        val selectedPointerLocation = DpOffset(2.dp, 4.dp)
        everySuspend {
            locationRepository.getCurrentLocationName(selectedCoordinates.toEntity())
        } calls { expectedAddress }

        // When
        createDukanViewModel.onMapClicked(selectedCoordinates, selectedPointerLocation)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val resultingAddress = createDukanViewModel.state.value.address
        assertEquals(resultingAddress, expectedAddress)
    }

    @Test
    fun `onMapClicked SHOULD return set the coordinates of selected location`() = runTest {
        // Given
        val expectedAddress = "Egypt"
        val selectedCoordinates = CreateDukanUiState.CoordinatesUiState(28.0, 29.0)
        val selectedPointerLocation = DpOffset(2.dp, 4.dp)
        everySuspend {
            locationRepository.getCurrentLocationName(selectedCoordinates.toEntity())
        } calls { expectedAddress }

        // When
        createDukanViewModel.onMapClicked(selectedCoordinates, selectedPointerLocation)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val resultingCoordinates = createDukanViewModel.state.value.currentLocation
        assertEquals(resultingCoordinates, selectedCoordinates)
    }

    @Test
    fun `onMapClicked SHOULD return set the coordinates location inside the composable of selected location`() =
        runTest {
            // Given
            val expectedAddress = "Egypt"
            val selectedCoordinates = CreateDukanUiState.CoordinatesUiState(28.0, 29.0)
            val selectedPointerLocation = DpOffset(2.dp, 4.dp)
            everySuspend {
                locationRepository.getCurrentLocationName(selectedCoordinates.toEntity())
            } calls { expectedAddress }

            // When
            createDukanViewModel.onMapClicked(selectedCoordinates, selectedPointerLocation)
            testDispatcher.scheduler.advanceUntilIdle()

            // Then
            val resultingPointerLocation = createDukanViewModel.state.value.pointerLocation
            assertEquals(resultingPointerLocation, selectedPointerLocation)
        }

    @Test
    fun `onEditMapLocationClicked SHOULD reset the address`() = runTest {
        // Given
        val startingAddress = "Egypt"
        val startingCoordinates = CreateDukanUiState.CoordinatesUiState(28.0, 29.0)
        val startingPointerLocation = DpOffset(2.dp, 4.dp)
        everySuspend {
            locationRepository.getCurrentLocationName(startingCoordinates.toEntity())
        } calls { startingAddress }
        createDukanViewModel.onMapClicked(startingCoordinates, startingPointerLocation)

        // When
        createDukanViewModel.onEditMapLocationClicked()

        // Then
        val resultingAddress = createDukanViewModel.state.value.address
        assertEquals("", resultingAddress)
    }

    @Test
    fun `onEditMapLocationClicked SHOULD current location`() = runTest {
        // Given
        val startingAddress = "Egypt"
        val startingCoordinates = CreateDukanUiState.CoordinatesUiState(28.0, 29.0)
        val startingPointerLocation = DpOffset(2.dp, 4.dp)
        everySuspend {
            locationRepository.getCurrentLocationName(startingCoordinates.toEntity())
        } calls { startingAddress }
        createDukanViewModel.onMapClicked(startingCoordinates, startingPointerLocation)

        // When
        createDukanViewModel.onEditMapLocationClicked()

        // Then
        val resultingLocation = createDukanViewModel.state.value.currentLocation
        assertEquals(CreateDukanUiState.CoordinatesUiState(), resultingLocation)
    }

    @Test
    fun `onEditMapLocationClicked SHOULD pointerLocation`() = runTest {
        // Given
        val startingAddress = "Egypt"
        val startingCoordinates = CreateDukanUiState.CoordinatesUiState(28.0, 29.0)
        val startingPointerLocation = DpOffset(2.dp, 4.dp)
        everySuspend {
            locationRepository.getCurrentLocationName(startingCoordinates.toEntity())
        } calls { startingAddress }
        createDukanViewModel.onMapClicked(startingCoordinates, startingPointerLocation)

        // When
        createDukanViewModel.onEditMapLocationClicked()

        // Then
        val resultingPointerLocation = createDukanViewModel.state.value.pointerLocation
        assertEquals(null, resultingPointerLocation)
    }

    @Test
    fun `onEditMapLocationClicked SHOULD disable the button`() = runTest {
        // Given
        val startingAddress = "Egypt"
        val startingCoordinates = CreateDukanUiState.CoordinatesUiState(28.0, 29.0)
        val startingPointerLocation = DpOffset(2.dp, 4.dp)
        everySuspend {
            locationRepository.getCurrentLocationName(startingCoordinates.toEntity())
        } calls { startingAddress }
        createDukanViewModel.onMapClicked(startingCoordinates, startingPointerLocation)

        // When
        createDukanViewModel.onEditMapLocationClicked()

        // Then
        val resultingButtonState = createDukanViewModel.state.value.isButtonEnabled
        assertFalse(resultingButtonState)
    }

    @Test
    fun `onCameraMoved SHOULD update the cameraPosition`() = runTest {
        // Given
        val expectedCameraPosition = CameraPosition(target = Position(29.0, 28.0))

        // When
        createDukanViewModel.onCameraMoved(expectedCameraPosition)

        // Then
        val resultingCameraPosition = createDukanViewModel.state.value.cameraPosition
        assertEquals(expectedCameraPosition, resultingCameraPosition)
    }

    @Test
    fun `when onColorClicked is called, then the ViewModel's state of selectedColor should be updated`() =
        runTest {
            // Given
            val color = ColorUiState(id = "1", color = 0xFFF545)

            // When
            createDukanViewModel.onColorClicked(color)

            // Then
            createDukanViewModel.state.test {
                val state = awaitItem()
                assertEquals(color, state.selectedColor)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when onStyleClicked is called, then the ViewModel's state of selectedStyle should be updated`() =
        runTest {
            // Given
            val style = Dukan.Style.WIDE_IMAGE

            // When
            createDukanViewModel.onStyleClicked(style)

            // Then
            createDukanViewModel.state.test {
                val state = awaitItem()
                assertEquals(style, state.selectedStyle)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `Given selectedStyle is not null and selectedColor is not null, when updateCreateButtonState is called, then isButtonEnabled should be true`() =
        runTest {
            // Given
            val style = Dukan.Style.WIDE_IMAGE
            val color = ColorUiState(id = "1", color = 0xFFF545)
            createDukanViewModel.onStyleClicked(style)
            createDukanViewModel.onColorClicked(color)

            // When
            createDukanViewModel.updateCreateButtonState()

            // Then
            createDukanViewModel.state.test {
                val state = awaitItem()
                assertEquals(true, state.isButtonEnabled)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `Given selectedStyle is null and selectedColor is not null, when updateCreateButtonState is called, then isButtonEnabled should be false`() =
        runTest {
            // Given
            val color = ColorUiState(id = "1", color = 0xFFF545)
            createDukanViewModel.onColorClicked(color)

            // When
            createDukanViewModel.updateCreateButtonState()

            // Then
            createDukanViewModel.state.test {
                val state = awaitItem()
                assertEquals(false, state.isButtonEnabled)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `Given selectedStyle is not null and selectedColor is null, when updateCreateButtonState is called, then isButtonEnabled should be false`() =
        runTest {
            // Given
            val style = Dukan.Style.WIDE_IMAGE
            createDukanViewModel.onStyleClicked(style)

            // When
            createDukanViewModel.updateCreateButtonState()

            // Then
            createDukanViewModel.state.test {
                val state = awaitItem()
                assertEquals(false, state.isButtonEnabled)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `Given selectedStyle is null and selectedColor is null, when updateCreateButtonState is called, then isButtonEnabled should be false`() =
        runTest {
            // When
            createDukanViewModel.updateCreateButtonState()

            // Then
            createDukanViewModel.state.test {
                val state = awaitItem()
                assertEquals(false, state.isButtonEnabled)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `onNameChanged SHOULD update name state with limited length`() = runTest {
        // Given
        val name = testName

        // When
        createDukanViewModel.onNameChanged(name)

        // Then
        val resultingName = createDukanViewModel.state.value.name
        assertEquals(name, resultingName)
    }

    @Test
    fun `onNameChanged SHOULD limit name to 40 characters when name is longer`() = runTest {
        // Given
        val expectedName = longName.take(40)

        // When
        createDukanViewModel.onNameChanged(longName)

        // Then
        val resultingName = createDukanViewModel.state.value.name
        assertEquals(expectedName, resultingName)
    }

    @Test
    fun `onNameChanged SHOULD hide snack bar when name is changed`() = runTest {
        // Given
        createDukanViewModel.updateState { copy(showSnackBar = true) }

        // When
        createDukanViewModel.onNameChanged(newName)

        // Then
        val showSnackBar = createDukanViewModel.state.value.showSnackBar
        assertFalse(showSnackBar)
    }

    @Test
    fun `onNameChanged SHOULD update next button state`() = runTest {
        // Given
        val name = testName

        // When
        createDukanViewModel.onNameChanged(name)

        // Then
        assertTrue(true)
    }

    @Test
    fun `isCategorySelected SHOULD return true when category is in selected categories`() =
        runTest {
            // Given
            createDukanViewModel.updateState { copy(selectedCategories = setOf(fakeCategories()[0].toUiState())) }

            // When
            val isSelected =
                createDukanViewModel.isCategorySelected()(fakeCategories()[0].toUiState())

            // Then
            assertTrue(isSelected)
        }

    @Test
    fun `isCategorySelected SHOULD return false when category is not in selected categories`() =
        runTest {
            // Given
            createDukanViewModel.updateState { copy(selectedCategories = setOf(fakeCategories()[1].toUiState())) }

            // When
            val isSelected =
                createDukanViewModel.isCategorySelected()(fakeCategories()[0].toUiState())

            // Then
            assertFalse(isSelected)
        }

    @Test
    fun `onCategorySelected SHOULD return true when can select more categories`() = runTest {
        // Given
        val category = fakeCategories()[0].toUiState()

        // When
        val result = createDukanViewModel.onCategorySelected(category)

        // Then
        assertTrue(result)
    }

    @Test
    fun `onCategorySelected SHOULD add category when can select more`() = runTest {
        // Given
        val category = fakeCategories()[0].toUiState()

        // When
        createDukanViewModel.onCategorySelected(category)

        // Then
        val selectedCategories = createDukanViewModel.state.value.selectedCategories
        assertTrue(selectedCategories.contains(category))
    }

    @Test
    fun `onCategorySelected SHOULD return false when max categories reached`() = runTest {
        // Given
        createDukanViewModel.updateState {
            copy(
                selectedCategories = setOf(
                    fakeCategories()[0].toUiState(),
                    fakeCategories()[1].toUiState(),
                    fakeCategories()[2].toUiState()
                )
            )
        }

        // When
        val result = createDukanViewModel.onCategorySelected(fakeCategories()[3].toUiState())

        // Then
        assertFalse(result)
        val selectedCategories = createDukanViewModel.state.value.selectedCategories
        assertFalse(selectedCategories.contains(fakeCategories()[3].toUiState()))
    }

    @Test
    fun `onCategoryDeselected SHOULD return true when category is removed`() = runTest {
        // Given
        createDukanViewModel.updateState { copy(selectedCategories = setOf(fakeCategories()[0].toUiState())) }

        // When
        val result = createDukanViewModel.onCategoryDeselected(fakeCategories()[0].toUiState())

        // Then
        assertTrue(result)
    }

    @Test
    fun `onCategoryDeselected SHOULD remove category from selection`() = runTest {
        // Given
        createDukanViewModel.updateState { copy(selectedCategories = setOf(fakeCategories()[0].toUiState())) }

        // When
        createDukanViewModel.onCategoryDeselected(fakeCategories()[0].toUiState())

        // Then
        val selectedCategories = createDukanViewModel.state.value.selectedCategories
        assertFalse(selectedCategories.contains(fakeCategories()[0].toUiState()))
    }

    @Test
    fun `onCategoryEnabled SHOULD return true when can select more categories`() = runTest {
        // Given
        val category = fakeCategories()[0].toUiState()

        // When
        val result = createDukanViewModel.onCategoryEnabled(category)

        // Then
        assertTrue(result)
    }

    @Test
    fun `onCategoryEnabled SHOULD return true when category is already selected`() = runTest {
        // Given
        createDukanViewModel.updateState {
            copy(
                selectedCategories = setOf(
                    fakeCategories()[0].toUiState(),
                    fakeCategories()[1].toUiState(),
                    fakeCategories()[2].toUiState()
                )
            )
        }

        // When
        val result = createDukanViewModel.onCategoryEnabled(fakeCategories()[0].toUiState())

        // Then
        assertTrue(result)
    }

    @Test
    fun `onCategoryEnabled SHOULD return false when max categories reached and category not selected`() =
        runTest {
            // Given
            createDukanViewModel.updateState {
                copy(
                    selectedCategories = setOf(
                        fakeCategories()[0].toUiState(),
                        fakeCategories()[1].toUiState(),
                        fakeCategories()[2].toUiState()
                    )
                )
            }

            // When
            val result = createDukanViewModel.onCategoryEnabled(fakeCategories()[3].toUiState())

            // Then
            assertFalse(result)
        }

    @Test
    fun `onCLickNext SHOULD trigger name validation when in basic information step`() = runTest {
        // Given
        createDukanViewModel.updateState {
            copy(
                name = testDukan,
                selectedCategories = setOf(fakeCategories()[0].toUiState()),
                currentStep = CreateDukanUiState.CreateDukanStep.BASIC_INFORMATION,
                showSnackBar = false
            )
        }
        everySuspend { dukanRepository.isDukanNameTaken(testDukan) } calls { false }

        // When
        createDukanViewModel.onCLickNext()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertTrue(true)
    }

    @Test
    fun `onCLickNext SHOULD show snack bar when basic information is invalid`() = runTest {
        // Given
        createDukanViewModel.updateState {
            copy(
                name = "",
                selectedCategories = setOf(fakeCategories()[0].toUiState()),
                currentStep = CreateDukanUiState.CreateDukanStep.BASIC_INFORMATION,
                showSnackBar = false
            )
        }

        // When
        createDukanViewModel.onCLickNext()

        // Then
        val state = createDukanViewModel.state.value
        assertTrue(state.showSnackBar)
        assertFalse(state.isNameUnique)
    }

    @Test
    fun `onCategorySelected SHOULD return false when maximum category limit reached`() = runTest {
        // Given
        createDukanViewModel.updateState {
            copy(
                selectedCategories = setOf(
                    fakeCategories()[0].toUiState(),
                    fakeCategories()[1].toUiState(),
                    fakeCategories()[2].toUiState()
                )
            )
        }

        // When
        val result = createDukanViewModel.onCategorySelected(fakeCategories()[3].toUiState())

        // Then
        assertFalse(result)
    }

    @Test
    fun `onCategorySelected SHOULD maintain category count when maximum limit reached`() = runTest {
        // Given
        createDukanViewModel.updateState {
            copy(
                selectedCategories = setOf(
                    fakeCategories()[0].toUiState(),
                    fakeCategories()[1].toUiState(),
                    fakeCategories()[2].toUiState()
                )
            )
        }

        // When
        createDukanViewModel.onCategorySelected(fakeCategories()[3].toUiState())

        // Then
        val selectedCategories = createDukanViewModel.state.value.selectedCategories
        assertEquals(3, selectedCategories.size)
    }

    @Test
    fun `onCategorySelected SHOULD not include new category when maximum limit reached`() =
        runTest {
            // Given
            createDukanViewModel.updateState {
                copy(
                    selectedCategories = setOf(
                        fakeCategories()[0].toUiState(),
                        fakeCategories()[1].toUiState(),
                        fakeCategories()[2].toUiState()
                    )
                )
            }

            // When
            createDukanViewModel.onCategorySelected(fakeCategories()[3].toUiState())

            // Then
            val selectedCategories = createDukanViewModel.state.value.selectedCategories
            assertFalse(selectedCategories.contains(fakeCategories()[3].toUiState()))
        }

    @Test
    fun `onCategoryDeselected SHOULD handle empty selection correctly`() = runTest {
        // Given
        createDukanViewModel.updateState { copy(selectedCategories = emptySet()) }

        // When
        val result = createDukanViewModel.onCategoryDeselected(fakeCategories()[0].toUiState())

        // Then
        assertTrue(result)
        val selectedCategories = createDukanViewModel.state.value.selectedCategories
        assertTrue(selectedCategories.isEmpty())
    }

    @Test
    fun `onNameChanged SHOULD update button state based on validation`() = runTest {
        // Given
        createDukanViewModel.updateState {
            copy(
                name = "",
                selectedCategories = setOf(fakeCategories()[0].toUiState()),
                currentStep = CreateDukanUiState.CreateDukanStep.BASIC_INFORMATION
            )
        }

        // When
        createDukanViewModel.onNameChanged(testDukan)

        // Then
        val state = createDukanViewModel.state.value
        assertTrue(state.isButtonEnabled)
    }

    @Test
    fun `onCategorySelected SHOULD update button state`() = runTest {
        // Given
        createDukanViewModel.updateState {
            copy(
                name = testDukan,
                selectedCategories = emptySet(),
                currentStep = CreateDukanUiState.CreateDukanStep.BASIC_INFORMATION
            )
        }

        // When
        createDukanViewModel.onCategorySelected(fakeCategories()[0].toUiState())

        // Then
        val state = createDukanViewModel.state.value
        assertTrue(state.isButtonEnabled)
    }

    @Test
    fun `onDismissSnackBar SHOULD hide snack bar when called`() = runTest {
        // Given
        createDukanViewModel.updateState { copy(showSnackBar = true) }

        // When
        createDukanViewModel.onDismissSnackBar()

        // Then
        val showSnackBar = createDukanViewModel.state.value.showSnackBar
        assertFalse(showSnackBar)
    }

    @Test
    fun `onDismissSnackBar SHOULD not affect other state properties`() = runTest {
        // Given
        val testName = "Test Dukan"
        val testCategories = setOf(fakeCategories()[0].toUiState(), fakeCategories()[1].toUiState())
        createDukanViewModel.updateState {
            copy(
                name = testName,
                selectedCategories = testCategories,
                showSnackBar = true,
                isNameUnique = false
            )
        }

        // When
        createDukanViewModel.onDismissSnackBar()

        // Then
        val state = createDukanViewModel.state.value
        assertFalse(state.showSnackBar)
        assertEquals(testName, state.name)
        assertEquals(testCategories, state.selectedCategories)
        assertFalse(state.isNameUnique)
    }

    @Test
    fun `onDismissSnackBar SHOULD work when snack bar is already hidden`() = runTest {
        // Given
        createDukanViewModel.updateState { copy(showSnackBar = false) }

        // When
        createDukanViewModel.onDismissSnackBar()

        // Then
        val showSnackBar = createDukanViewModel.state.value.showSnackBar
        assertFalse(showSnackBar)
    }
}

private const val testName = "Test Dukan Name"
private const val longName =
    "This is a very long dukan name that exceeds the maximum allowed length of forty characters"
private const val newName = "New Dukan Name"
private const val testDukan = "Test Dukan"

private fun fakeDukanColor(): List<Color> {
    return listOf(
        Color(id = "1", hexCode = "#F77053"),
        Color(id = "2", hexCode = "#F4C343"),
        Color(id = "3", hexCode = "#C30C30"),
        Color(id = "4", hexCode = "#30ABE8")
    )
}

private fun fakeDukanStyle(): List<Dukan.Style> {
    return listOf(
        Dukan.Style.WIDE_IMAGE,
        Dukan.Style.SMALL_IMAGE,
        Dukan.Style.NO_IMAGE
    )
}

private fun fakeCategories(): List<Category> {
    return listOf(
        Category(id = "1", name = "Electronics", imageUrl = "https://example.com/electronics.png"),
        Category(id = "2", name = "Clothes", imageUrl = "https://example.com/clothes.png"),
        Category(id = "3", name = "Groceries", imageUrl = "https://example.com/groceries.png"),
        Category(id = "4", name = "Books", imageUrl = "https://example.com/books.png")
    )
}

private fun Category.toUiState() = DukanCategoryUiState(
    id = id,
    name = name,
    imageUrl = imageUrl
)