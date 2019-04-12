package com.abnerescocio.edittextmask

import com.abnerescocio.lib.MASK
import org.junit.Test
import org.junit.Assert.*

class CpfTest {
    
    @Test
    fun equals_numbersIsNotAllowed() {
        assertEquals(MASK.CPF.isValid("00000000000"), false)
        assertEquals(MASK.CPF.isValid("000.000.000-00"), false)
        assertEquals(MASK.CPF.isValid("11111111111"), false)
        assertEquals(MASK.CPF.isValid("111.111.111-11"), false)
        assertEquals(MASK.CPF.isValid("22222222222"), false)
        assertEquals(MASK.CPF.isValid("222.222.222-22"), false)
        assertEquals(MASK.CPF.isValid("33333333333"), false)
        assertEquals(MASK.CPF.isValid("333.333.333-33"), false)
        assertEquals(MASK.CPF.isValid("44444444444"), false)
        assertEquals(MASK.CPF.isValid("444.444.444-44"), false)
        assertEquals(MASK.CPF.isValid("55555555555"), false)
        assertEquals(MASK.CPF.isValid("555.555.555-55"), false)
        assertEquals(MASK.CPF.isValid("66666666666"), false)
        assertEquals(MASK.CPF.isValid("666.666.666-66"), false)
        assertEquals(MASK.CPF.isValid("77777777777"), false)
        assertEquals(MASK.CPF.isValid("777.777.777-77"), false)
        assertEquals(MASK.CPF.isValid("88888888888"), false)
        assertEquals(MASK.CPF.isValid("888.888.888-88"), false)
        assertEquals(MASK.CPF.isValid("99999999999"), false)
        assertEquals(MASK.CPF.isValid("999.999.999-99"), false)
    }
    
    @Test
    fun valid_maskIsAllowed() {
        assertEquals(MASK.CPF.isValid("110.342.820-93"), true)
    }
    
    @Test
    fun invalid_maskIsNotAllowed() {
        assertEquals(MASK.CPF.isValid("110.34282093"), false)
        assertEquals(MASK.CPF.isValid("110.342.82093"), false)
        assertEquals(MASK.CPF.isValid("110342.820-93"), false)
        assertEquals(MASK.CPF.isValid("110.342820-93"), false)
    }
    
    @Test
    fun valid_numbersIsAllowed() {
        assertEquals(MASK.CPF.isValid("53058032099"), true)
        assertEquals(MASK.CPF.isValid("39512741822"), true)
    }
    
    @Test
    fun invalid_numbersIsNotAllowed() {
        assertEquals(MASK.CPF.isValid("12345678900"), false)
    }
    
}