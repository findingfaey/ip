package arin.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;

/**
 * Tests for UI components.
 * Note: Some tests require JavaFX thread, so they're mainly for design verification.
 */
public class DialogBoxTest {

    /**
     * Test that profile pictures are properly configured.
     * This is a design verification test to prevent image cutoff issues.
     */
    @Test
    public void makeProfilePictureCircular_shouldPreserveRatio() throws Exception {
        // This is a design verification test - can't fully run without JavaFX thread
        // But we can verify the method exists and contains expected logic

        Class<?> dialogBoxClass = DialogBox.class;
        Method makeCircularMethod = null;

        try {
            makeCircularMethod = dialogBoxClass.getDeclaredMethod("makeProfilePictureCircular");
            makeCircularMethod.setAccessible(true);
            assertNotNull(makeCircularMethod, "Method should exist");
        } catch (NoSuchMethodException e) {
            fail("makeProfilePictureCircular method should exist in DialogBox class");
        }

        // In reality, we'd need a TestFX framework to fully test JavaFX components
    }

    /**
     * Test DialogBox creation logic.
     */
    @Test
    public void dialogBox_creation_shouldNotThrowExceptions() {
        // Again, partial test without JavaFX thread
        // Just verify the factory methods exist with correct signatures

        try {
            Class<?> dialogBoxClass = DialogBox.class;
            Method getUserDialogMethod = dialogBoxClass.getDeclaredMethod(
                    "getUserDialog", String.class, Image.class);
            Method getArinDialogMethod = dialogBoxClass.getDeclaredMethod(
                    "getArinDialog", String.class, Image.class);
            Method getErrorDialogMethod = dialogBoxClass.getDeclaredMethod(
                    "getErrorDialog", String.class, Image.class);

            assertNotNull(getUserDialogMethod, "getUserDialog method should exist");
            assertNotNull(getArinDialogMethod, "getArinDialog method should exist");
            assertNotNull(getErrorDialogMethod, "getErrorDialog method should exist");
        } catch (NoSuchMethodException e) {
            fail("DialogBox should have factory methods for creating different dialog types");
        }
    }
}
