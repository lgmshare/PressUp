package cn.body.pressup.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import cn.body.pressup.databases.AppDatabase
import kotlinx.android.parcel.Parcelize

@Entity(tableName = AppDatabase.T_BP)
@Parcelize
data class BloodPress(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var systolic: Int = 0,
    var diastolic: Int = 0,
    var pulse: Int = 0,
    var datetime: Long = 0,
) : Parcelable {

    companion object {
        const val LEVEL_A = 1
        const val LEVEL_B = 2
        const val LEVEL_C = 3
        const val LEVEL_D = 4
        const val LEVEL_E = 5
        const val LEVEL_F = 6
    }

    fun level(): Int {
        if (systolic in 90..119 && diastolic in 60..79) {
            return LEVEL_B
        }
        if (systolic in 120..129 && diastolic in 60..79) {
            return LEVEL_C
        }
        if (systolic < 90 || diastolic < 60) {
            return LEVEL_A
        }
        if (systolic > 180 || diastolic > 120) {
            return LEVEL_F
        }
        if (systolic in 140..180 || diastolic in 90..120) {
            return LEVEL_E
        }
        if (systolic in 130..139 || diastolic in 80..89) {
            return LEVEL_D
        }
        return LEVEL_B
    }

}