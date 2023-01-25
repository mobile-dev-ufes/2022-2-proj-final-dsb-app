package com.example.dsb_app.data.model


class TeamModel {

   /* @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")*/
    var id: Int = 0

   /* @ColumnInfo(name="name")*/
    var name: String = ""

   /* @ColumnInfo(name="prod_code")*/
    var prodCode: String = ""

   /* @ColumnInfo(name="price")*/
    var price: Float = 0f

 /*   @ColumnInfo(name="amount")*/
    var amount: Int = 0
}