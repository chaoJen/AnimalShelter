package com.mrr.animalshelter.data.element

import androidx.annotation.Keep
import androidx.annotation.StringRes
import com.mrr.animalshelter.R

@Keep
enum class AnimalArea(
    val id: Int,
    @StringRes val nameResourceId: Int,
    @StringRes val badgeNameResourceId: Int
) {
    All(-1, R.string.area_all, R.string.area_all_badge),
    PkId02(2, R.string.area_pk_id_02, R.string.area_pk_id_02_badge),
    PkId03(3, R.string.area_pk_id_03, R.string.area_pk_id_03_badge),
    PkId04(4, R.string.area_pk_id_04, R.string.area_pk_id_04_badge),
    PkId05(5, R.string.area_pk_id_05, R.string.area_pk_id_05_badge),
    PkId06(6, R.string.area_pk_id_06, R.string.area_pk_id_06_badge),
    PkId07(7, R.string.area_pk_id_07, R.string.area_pk_id_07_badge),
    PkId08(8, R.string.area_pk_id_08, R.string.area_pk_id_08_badge),
    PkId09(9, R.string.area_pk_id_09, R.string.area_pk_id_09_badge),
    PkId10(10, R.string.area_pk_id_10, R.string.area_pk_id_10_badge),
    PkId11(11, R.string.area_pk_id_11, R.string.area_pk_id_11_badge),
    PkId12(12, R.string.area_pk_id_12, R.string.area_pk_id_12_badge),
    PkId13(13, R.string.area_pk_id_13, R.string.area_pk_id_13_badge),
    PkId14(14, R.string.area_pk_id_14, R.string.area_pk_id_14_badge),
    PkId15(15, R.string.area_pk_id_15, R.string.area_pk_id_15_badge),
    PkId16(16, R.string.area_pk_id_16, R.string.area_pk_id_16_badge),
    PkId17(17, R.string.area_pk_id_17, R.string.area_pk_id_17_badge),
    PkId18(18, R.string.area_pk_id_18, R.string.area_pk_id_18_badge),
    PkId19(19, R.string.area_pk_id_19, R.string.area_pk_id_19_badge),
    PkId20(20, R.string.area_pk_id_20, R.string.area_pk_id_20_badge),
    PkId21(21, R.string.area_pk_id_21, R.string.area_pk_id_21_badge),
    PkId22(22, R.string.area_pk_id_22, R.string.area_pk_id_22_badge),
    PkId23(23, R.string.area_pk_id_23, R.string.area_pk_id_23_badge);

    companion object {
        fun find(areaPkId: Int): AnimalArea? = values().find { it.id == areaPkId }
    }
}


