package com.mrr.animalshelter.data.element

import androidx.annotation.StringRes
import com.mrr.animalshelter.R

enum class AnimalArea(val id: Int, @StringRes val nameResourceId: Int) {
    All(-1, R.string.area_all),
    PkId02(2, R.string.area_pk_id_02),
    PkId03(3, R.string.area_pk_id_03),
    PkId04(4, R.string.area_pk_id_04),
    PkId05(5, R.string.area_pk_id_05),
    PkId06(6, R.string.area_pk_id_06),
    PkId07(7, R.string.area_pk_id_07),
    PkId08(8, R.string.area_pk_id_08),
    PkId09(9, R.string.area_pk_id_09),
    PkId10(10, R.string.area_pk_id_10),
    PkId11(11, R.string.area_pk_id_11),
    PkId12(12, R.string.area_pk_id_12),
    PkId13(13, R.string.area_pk_id_13),
    PkId14(14, R.string.area_pk_id_14),
    PkId15(15, R.string.area_pk_id_15),
    PkId16(16, R.string.area_pk_id_16),
    PkId17(17, R.string.area_pk_id_17),
    PkId18(18, R.string.area_pk_id_18),
    PkId19(19, R.string.area_pk_id_19),
    PkId20(20, R.string.area_pk_id_20),
    PkId21(21, R.string.area_pk_id_21),
    PkId22(22, R.string.area_pk_id_22),
    PkId23(23, R.string.area_pk_id_23);

    companion object {
        fun find(areaPkId: Int): AnimalArea? = values().find { it.id == areaPkId }
    }
}


