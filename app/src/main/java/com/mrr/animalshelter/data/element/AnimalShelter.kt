package com.mrr.animalshelter.data.element

import androidx.annotation.StringRes
import com.mrr.animalshelter.R

enum class AnimalShelter(val id: Int, val area: AnimalArea, @StringRes val nameResourceId: Int) {
    All(-1, AnimalArea.All, R.string.shelter_all),
    PkId49(49, AnimalArea.PkId02, R.string.shelter_pk_id_49),
    PkId50(50, AnimalArea.PkId03, R.string.shelter_pk_id_50),
    PkId51(51, AnimalArea.PkId03, R.string.shelter_pk_id_51),
    PkId53(53, AnimalArea.PkId03, R.string.shelter_pk_id_53),
    PkId55(55, AnimalArea.PkId03, R.string.shelter_pk_id_55),
    PkId56(56, AnimalArea.PkId03, R.string.shelter_pk_id_56),
    PkId58(58, AnimalArea.PkId03, R.string.shelter_pk_id_58),
    PkId59(59, AnimalArea.PkId03, R.string.shelter_pk_id_59),
    PkId60(60, AnimalArea.PkId03, R.string.shelter_pk_id_60),
    PkId92(92, AnimalArea.PkId03, R.string.shelter_pk_id_92),
    PkId48(48, AnimalArea.PkId04, R.string.shelter_pk_id_48),
    PkId78(78, AnimalArea.PkId05, R.string.shelter_pk_id_78),
    PkId61(61, AnimalArea.PkId06, R.string.shelter_pk_id_61),
    PkId63(63, AnimalArea.PkId07, R.string.shelter_pk_id_63),
    PkId62(62, AnimalArea.PkId08, R.string.shelter_pk_id_62),
    PkId96(96, AnimalArea.PkId09, R.string.shelter_pk_id_96),
    PkId67(67, AnimalArea.PkId10, R.string.shelter_pk_id_67),
    PkId68(68, AnimalArea.PkId10, R.string.shelter_pk_id_68),
    PkId69(69, AnimalArea.PkId11, R.string.shelter_pk_id_69),
    PkId70(70, AnimalArea.PkId12, R.string.shelter_pk_id_70),
    PkId89(89, AnimalArea.PkId13, R.string.shelter_pk_id_89),
    PkId72(72, AnimalArea.PkId14, R.string.shelter_pk_id_72),
    PkId71(71, AnimalArea.PkId15, R.string.shelter_pk_id_71),
    PkId73(73, AnimalArea.PkId16, R.string.shelter_pk_id_73),
    PkId74(74, AnimalArea.PkId16, R.string.shelter_pk_id_74),
    PkId75(75, AnimalArea.PkId17, R.string.shelter_pk_id_75),
    PkId76(76, AnimalArea.PkId17, R.string.shelter_pk_id_76),
    PkId77(77, AnimalArea.PkId18, R.string.shelter_pk_id_77),
    PkId79(79, AnimalArea.PkId19, R.string.shelter_pk_id_79),
    PkId80(80, AnimalArea.PkId20, R.string.shelter_pk_id_80),
    PkId83(83, AnimalArea.PkId21, R.string.shelter_pk_id_83),
    PkId82(82, AnimalArea.PkId22, R.string.shelter_pk_id_82),
    PkId81(81, AnimalArea.PkId23, R.string.shelter_pk_id_81);

    companion object {
        fun find(area: AnimalArea): MutableList<AnimalShelter> {
            val shelters = values()
                .filter { area == AnimalArea.All || it.area == area }
                .toMutableList()

            if (area != AnimalArea.All && shelters.size > 1) {
                shelters.add(0, All)
            }
            return shelters
        }
    }
}