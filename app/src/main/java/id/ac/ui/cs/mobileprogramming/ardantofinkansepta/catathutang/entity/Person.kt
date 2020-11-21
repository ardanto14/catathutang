package id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Person(

    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    val name: String,

    @ColumnInfo(name="initial_value") val initialValue: Int,

    val value: Int

)