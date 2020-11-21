package id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Transaction (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val value: Int,
    val note: String
)