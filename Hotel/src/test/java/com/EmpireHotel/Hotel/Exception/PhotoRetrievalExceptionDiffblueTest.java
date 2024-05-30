package com.EmpireHotel.Hotel.Exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

class PhotoRetrievalExceptionDiffblueTest {
    /**
     * Method under test:
     * {@link PhotoRetrievalException#PhotoRetrievalException(String, Throwable)}
     */
    @Test
    void testConstructor() {
        Throwable cause = new Throwable();
        PhotoRetrievalException actualPhotoRetrievalException = new PhotoRetrievalException("An error occurred", cause);

        assertEquals("An error occurred", actualPhotoRetrievalException.getLocalizedMessage());
        assertEquals("An error occurred", actualPhotoRetrievalException.getMessage());
        Throwable cause2 = actualPhotoRetrievalException.getCause();
        assertNull(cause2.getLocalizedMessage());
        assertNull(cause2.getMessage());
        assertNull(cause2.getCause());
        Throwable[] suppressed = actualPhotoRetrievalException.getSuppressed();
        assertEquals(0, suppressed.length);
        assertSame(cause, cause2);
        assertSame(suppressed, cause2.getSuppressed());
    }
}
