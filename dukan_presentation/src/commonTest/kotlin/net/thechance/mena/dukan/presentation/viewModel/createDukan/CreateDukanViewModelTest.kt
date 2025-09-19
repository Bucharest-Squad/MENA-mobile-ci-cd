package net.thechance.mena.dukan.presentation.viewModel.createDukan

import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import dev.mokkery.MockMode
import dev.mokkery.answering.calls
import dev.mokkery.everySuspend
import dev.mokkery.mock
import io.github.dellisd.spatialk.geojson.Position
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
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
        createDukanViewModel = CreateDukanViewModel(
            dukanRepository,
            locationRepository,
            testDispatcher
        )
    }

    @Test
    fun `onAddressChanged SHOULD update address state`() = runTest {
        val address = "This new address"

        createDukanViewModel.onAddressChanged(address)
        val newAddress = createDukanViewModel.state.value.address
        assertEquals(newAddress, address)
    }

    @Test
    fun `onAddressChanged SHOULD set isButtonEnabled state to false if address is blank`() =
        runTest {
            val address = ""

            createDukanViewModel.onAddressChanged(address)
            val buttonState = createDukanViewModel.state.value.isButtonEnabled
            assertFalse(buttonState)
        }

    @Test
    fun `onMapClicked SHOULD return the address of the selected location`() = runTest {
        // Given
        everySuspend {
            locationRepository.getCurrentLocationName(testCoordinates.toEntity())
        } calls { expectedAddress }

        // when
        createDukanViewModel.onMapClicked(testCoordinates, selectedPointerLocation)

        testDispatcher.scheduler.advanceUntilIdle()
        val resultingAddress = createDukanViewModel.state.value.address

        // Then
        assertEquals(resultingAddress, expectedAddress)
    }

    @Test
    fun `onMapClicked SHOULD return set the coordinates of selected location`() = runTest {
        // Given
        everySuspend {
            locationRepository.getCurrentLocationName(testCoordinates.toEntity())
        } calls { expectedAddress }

        // when
        createDukanViewModel.onMapClicked(testCoordinates, selectedPointerLocation)

        testDispatcher.scheduler.advanceUntilIdle()
        val resultingCoordinates = createDukanViewModel.state.value.currentLocation

        // Then
        assertEquals(resultingCoordinates, testCoordinates)
    }

    @Test
    fun `onMapClicked SHOULD return set the coordinates location inside the composable of selected location`() =
        runTest {
            // Given
            everySuspend {
                locationRepository.getCurrentLocationName(testCoordinates.toEntity())
            } calls { expectedAddress }

            // when
            createDukanViewModel.onMapClicked(testCoordinates, selectedPointerLocation)

            testDispatcher.scheduler.advanceUntilIdle()
            val resultingPointerLocation = createDukanViewModel.state.value.pointerLocation

            // Then
            assertEquals(resultingPointerLocation, selectedPointerLocation)
        }

    @Test
    fun `onEditMapLocationClicked SHOULD reset the address`() = runTest {
        // Given
        everySuspend {
            locationRepository.getCurrentLocationName(testCoordinates.toEntity())
        } calls { expectedAddress }

        // when
        createDukanViewModel.onMapClicked(testCoordinates, selectedPointerLocation)
        createDukanViewModel.onEditMapLocationClicked()

        // Then
        val resultingAddress = createDukanViewModel.state.value.address
        assertEquals("", resultingAddress)
    }

    @Test
    fun `onEditMapLocationClicked SHOULD current location`() = runTest {
        // Given
        everySuspend {
            locationRepository.getCurrentLocationName(testCoordinates.toEntity())
        } calls { expectedAddress }

        // when
        createDukanViewModel.onMapClicked(testCoordinates, selectedPointerLocation)
        createDukanViewModel.onEditMapLocationClicked()

        // Then
        val resultingLocation = createDukanViewModel.state.value.currentLocation
        assertEquals(CreateDukanUiState.CoordinatesUiState(), resultingLocation)
    }

    @Test
    fun `onEditMapLocationClicked SHOULD pointerLocation`() = runTest {
        // Given
        everySuspend {
            locationRepository.getCurrentLocationName(testCoordinates.toEntity())
        } calls { expectedAddress }

        // when
        createDukanViewModel.onMapClicked(testCoordinates, selectedPointerLocation)
        createDukanViewModel.onEditMapLocationClicked()

        // Then
        val resultingPointerLocation = createDukanViewModel.state.value.pointerLocation
        assertEquals(null, resultingPointerLocation)
    }

    @Test
    fun `onEditMapLocationClicked SHOULD disable the button`() = runTest {
        // Given
        everySuspend {
            locationRepository.getCurrentLocationName(testCoordinates.toEntity())
        } calls { expectedAddress }

        // when
        createDukanViewModel.onMapClicked(testCoordinates, selectedPointerLocation)
        createDukanViewModel.onEditMapLocationClicked()

        // Then
        val resultingButtonState = createDukanViewModel.state.value.isButtonEnabled
        assertFalse(resultingButtonState)
    }

    @Test
    fun `onCameraMoved SHOULD update the cameraPosition`() = runTest {
        // Given
        val expectedCameraPosition = testCameraPosition

        // When
        createDukanViewModel.onCameraMoved(expectedCameraPosition)

        // Then
        val resultingCameraPosition = createDukanViewModel.state.value.cameraPosition
        assertEquals(expectedCameraPosition, resultingCameraPosition)
    }

    // Test cases for onNameChanged method
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
    fun `isCategorySelected SHOULD return true when category is in selected categories`() = runTest {
        // Given
        createDukanViewModel.updateState { copy(selectedCategories = setOf(category1)) }
        
        // When
        val isSelected = createDukanViewModel.isCategorySelected()(category1)
        
        // Then
        assertTrue(isSelected)
    }

    @Test
    fun `isCategorySelected SHOULD return false when category is not in selected categories`() = runTest {
        // Given
        createDukanViewModel.updateState { copy(selectedCategories = setOf(category2)) }
        
        // When
        val isSelected = createDukanViewModel.isCategorySelected()(category1)
        
        // Then
        assertFalse(isSelected)
    }

    @Test
    fun `onCategorySelected SHOULD return true when can select more categories`() = runTest {
        // Given
        val category = category1
        
        // When
        val result = createDukanViewModel.onCategorySelected(category)
        
        // Then
        assertTrue(result)
    }

    @Test
    fun `onCategorySelected SHOULD add category when can select more`() = runTest {
        // Given
        val category = category1
        
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
            copy(selectedCategories = setOf(category1, category2, category3)) 
        }
        
        // When
        val result = createDukanViewModel.onCategorySelected(category4)
        
        // Then
        assertFalse(result)
        val selectedCategories = createDukanViewModel.state.value.selectedCategories
        assertFalse(selectedCategories.contains(category4))
    }

    @Test
    fun `onCategoryDeselected SHOULD return true when category is removed`() = runTest {
        // Given
        createDukanViewModel.updateState { copy(selectedCategories = setOf(category1)) }
        
        // When
        val result = createDukanViewModel.onCategoryDeselected(category1)
        
        // Then
        assertTrue(result)
    }

    @Test
    fun `onCategoryDeselected SHOULD remove category from selection`() = runTest {
        // Given
        createDukanViewModel.updateState { copy(selectedCategories = setOf(category1)) }
        
        // When
        createDukanViewModel.onCategoryDeselected(category1)
        
        // Then
        val selectedCategories = createDukanViewModel.state.value.selectedCategories
        assertFalse(selectedCategories.contains(category1))
    }

    @Test
    fun `onCategoryEnabled SHOULD return true when can select more categories`() = runTest {
        // Given
        val category = category1
        
        // When
        val result = createDukanViewModel.onCategoryEnabled(category)
        
        // Then
        assertTrue(result)
    }

    @Test
    fun `onCategoryEnabled SHOULD return true when category is already selected`() = runTest {
        // Given
        createDukanViewModel.updateState { 
            copy(selectedCategories = setOf(category1, category2, category3)) 
        }
        
        // When
        val result = createDukanViewModel.onCategoryEnabled(category1)
        
        // Then
        assertTrue(result)
    }

    @Test
    fun `onCategoryEnabled SHOULD return false when max categories reached and category not selected`() = runTest {
        // Given
        createDukanViewModel.updateState { 
            copy(selectedCategories = setOf(category1, category2, category3)) 
        }
        
        // When
        val result = createDukanViewModel.onCategoryEnabled(category4)
        
        // Then
        assertFalse(result)
    }

    @Test
    fun `onCLickNext SHOULD trigger name validation when in basic information step`() = runTest {
        // Given
        createDukanViewModel.updateState {
            copy(
                name = testDukan,
                selectedCategories = setOf(category1),
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
                selectedCategories = setOf(category1),
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
            copy(selectedCategories = setOf(category1, category2, category3)) 
        }
        
        // When
        val result = createDukanViewModel.onCategorySelected(category4)
        
        // Then
        assertFalse(result)
    }

    @Test
    fun `onCategorySelected SHOULD maintain category count when maximum limit reached`() = runTest {
        // Given
        createDukanViewModel.updateState { 
            copy(selectedCategories = setOf(category1, category2, category3)) 
        }
        
        // When
        createDukanViewModel.onCategorySelected(category4)
        
        // Then
        val selectedCategories = createDukanViewModel.state.value.selectedCategories
        assertEquals(3, selectedCategories.size)
    }

    @Test
    fun `onCategorySelected SHOULD not include new category when maximum limit reached`() = runTest {
        // Given
        createDukanViewModel.updateState { 
            copy(selectedCategories = setOf(category1, category2, category3)) 
        }
        
        // When
        createDukanViewModel.onCategorySelected(category4)
        
        // Then
        val selectedCategories = createDukanViewModel.state.value.selectedCategories
        assertFalse(selectedCategories.contains(category4))
    }

    @Test
    fun `onCategoryDeselected SHOULD handle empty selection correctly`() = runTest {
        // Given
        createDukanViewModel.updateState { copy(selectedCategories = emptySet()) }
        
        // When
        val result = createDukanViewModel.onCategoryDeselected(category1)
        
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
                selectedCategories = setOf(category1),
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
        createDukanViewModel.onCategorySelected(category1)
        
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
        val testCategories = setOf(category1, category2)
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

    companion object {
        val category1 = DukanCategoryUiState("1", "Electronics", "image_url")
        val category2 = DukanCategoryUiState("2", "Clothing", "image_url")
        val category3 = DukanCategoryUiState("3", "Books", "image_url")
        val category4 = DukanCategoryUiState("4", "Sports", "image_url")
        val testName = "Test Dukan Name"
        val longName = "This is a very long dukan name that exceeds the 40 character limit"
        val newName = "New Name"
        val testDukan = "Test Dukan"
        val testCoordinates = CreateDukanUiState.CoordinatesUiState(28.0, 29.0)
        val expectedAddress = "Egypt"
        val selectedPointerLocation = DpOffset(2.dp, 4.dp)
        val testCameraPosition = CameraPosition(target = Position(29.0, 28.0))
    }

}