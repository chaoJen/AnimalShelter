package com.mrr.animalshelter.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "CollectedAnimals")
data class Animal(

    @PrimaryKey
    @ColumnInfo(name = "animal_id")
    @SerializedName("animal_id")
    val animalId: Int, // 動物的流水編號

    @ColumnInfo(name = "animal_subid")
    @SerializedName("animal_subid")
    val animalSubId: String, // 動物的區域編號

    @ColumnInfo(name = "animal_area_pkid")
    @SerializedName("animal_area_pkid")
    val animalAreaPkId: Int, // 動物所屬縣市代碼

    @ColumnInfo(name = "animal_shelter_pkid")
    @SerializedName("animal_shelter_pkid")
    val animalShelterPkId: Int, // 動物所屬收容所代碼

    @ColumnInfo(name = "animal_place")
    @SerializedName("animal_place")
    val animalPlace: String, // 動物的實際所在地

    @ColumnInfo(name = "animal_kind")
    @SerializedName("animal_kind")
    val animalKind: String, // 動物的類型

    @ColumnInfo(name = "animal_sex")
    @SerializedName("animal_sex")
    val animalSex: String, // 動物性別

    @ColumnInfo(name = "animal_bodytype")
    @SerializedName("animal_bodytype")
    val animalBodyType: String, // 動物體型

    @ColumnInfo(name = "animal_colour")
    @SerializedName("animal_colour")
    val animalColour: String, // 動物毛色

    @ColumnInfo(name = "animal_age")
    @SerializedName("animal_age")
    val animalAge: String, // 動物年紀

    @ColumnInfo(name = "animal_sterilization")
    @SerializedName("animal_sterilization")
    val animalSterilization: String, // 是否絕育

    @ColumnInfo(name = "animal_bacterin")
    @SerializedName("animal_bacterin")
    val animalBacterin: String, // 是否施打狂犬病疫苗

    @ColumnInfo(name = "animal_foundplace")
    @SerializedName("animal_foundplace")
    val animalFoundPlace: String, // 動物尋獲地

    @ColumnInfo(name = "animal_title")
    @SerializedName("animal_title")
    val animalTitle: String, // 動物網頁標題

    @ColumnInfo(name = "animal_status")
    @SerializedName("animal_status")
    val animalStatus: String, // 動物狀態

    @ColumnInfo(name = "animal_remark")
    @SerializedName("animal_remark")
    val animalRemark: String, // 資料備註

    @ColumnInfo(name = "animal_caption")
    @SerializedName("animal_caption")
    val animalCaption: String, // 其他說明

    @ColumnInfo(name = "animal_opendate")
    @SerializedName("animal_opendate")
    val animalOpenDate: String, // 開放認養時間(起)

    @ColumnInfo(name = "animal_closeddate")
    @SerializedName("animal_closeddate")
    val animalClosedDate: String, // 開放認養時間(迄)

    @ColumnInfo(name = "animal_update")
    @SerializedName("animal_update")
    val animalUpdate: String, // 動物資料異動時間

    @ColumnInfo(name = "animal_createtime")
    @SerializedName("animal_createtime")
    val animalCreateTime: String, // 動物資料建立時間

    @ColumnInfo(name = "shelter_name")
    @SerializedName("shelter_name")
    val shelterName: String, // 動物所屬收容所名稱

    @ColumnInfo(name = "album_file")
    @SerializedName("album_file")
    val albumFile: String, // 圖片名稱

    @ColumnInfo(name = "album_update")
    @SerializedName("album_update")
    val albumUpdate: String, // 異動時間

    @ColumnInfo(name = "cDate")
    @SerializedName("cDate")
    val cDate: String, // 資料更新時間

    @ColumnInfo(name = "shelter_address")
    @SerializedName("shelter_address")
    val shelterAddress: String, // 地址

    @ColumnInfo(name = "shelter_tel")
    @SerializedName("shelter_tel")
    val shelterTel: String // 聯絡電話等欄位資訊

) : Serializable