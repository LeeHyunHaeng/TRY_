package com.yjk.atry.db.datamodel

import androidx.room.Entity

/**
 * 참고용 데이터 모델
 */
@Entity ( tableName = BaseDataModel.TABLE_NAME)
abstract class BaseDataModel(){

    /**
     * TABLE NAME 재 정의 필수
     */
    companion object {
        const val TABLE_NAME = "base"
    }

}