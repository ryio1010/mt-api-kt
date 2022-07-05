package com.muscletracking.mtapi.dao

import com.google.common.truth.Truth.assertThat
import com.muscletracking.mtapi.DaoBaseTest
import com.muscletracking.mtapi.dao.user.UserDao
import com.muscletracking.mtapi.entity.user.UserEntity
import com.ninja_squad.dbsetup.DbSetup
import com.ninja_squad.dbsetup.Operations.deleteAllFrom
import com.ninja_squad.dbsetup.Operations.sequenceOf
import com.ninja_squad.dbsetup.destination.DataSourceDestination
import com.ninja_squad.dbsetup.destination.Destination
import com.ninja_squad.dbsetup.operation.DeleteAll
import com.ninja_squad.dbsetup_kotlin.insertInto
import junit.framework.TestCase.assertTrue
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime
import javax.sql.DataSource

/**
 * DbSetupを利用したテストを行う
 * DbSetupは原則としてテーブルを全削除・データのインサート・テストの順で実行する
 * 原則としてテスト終了後、データの削除は行わない
 * TestContainersを利用してテストを行う
 */
@SpringBootTest
internal class UserDaoTest: DaoBaseTest() {

    @Autowired
    lateinit var dataSource: DataSource

    @Autowired
    lateinit var userDao: UserDao

    // DBSetup設定
    lateinit var destination: Destination
    lateinit var deleteAllUserTable: DeleteAll

    @BeforeEach
    fun setUp() {
        destination = DataSourceDestination(dataSource)
        deleteAllUserTable = deleteAllFrom("m_user")
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun test() {
        assertTrue(postgres.isRunning)
    }

    @Test
    @DisplayName("ユーザーIDでユーザーを1件取得できる")
    fun selectByIdTest() {
        // db test data setup
        val insertTestUser = insertInto("m_user") {
            withDefaultValue("regdate", LocalDateTime.now())
            withDefaultValue("upddate", LocalDateTime.now())
            withDefaultValue("version", 1)
            columns("userid", "username", "password", "regid", "updid")
            values("test1", "テストユーザー", "test1", "test1", "test1")
        }

        val operation = sequenceOf(deleteAllUserTable, insertTestUser)
        DbSetup(destination, operation).launch()

        // dbselect
        val actual = userDao.selectById("test1")

        // assertion
        assertThat(actual).isNotNull()
        assertThat(actual?.userId).isEqualTo("test1")
        assertThat(actual?.userName).isEqualTo("テストユーザー")
        assertThat(actual?.password).isEqualTo("test1")
        assertThat(actual?.regId).isEqualTo("test1")
        assertThat(actual?.updId).isEqualTo("test1")
    }

    @Test
    @DisplayName("新規ユーザー1件登録できる")
    fun insertNewUserTest() {
        val operation = sequenceOf(deleteAllUserTable)
        DbSetup(destination, operation).launch()

        val newUser =
            UserEntity(userId = "test1", userName = "テストユーザー", password = "test1", regId = "test1", updId = "test1")
        val insert = userDao.insertNewUser(newUser)

        val actual = insert.entity

        assertThat(actual).isNotNull()
        assertThat(actual?.userId).isEqualTo("test1")
        assertThat(actual?.userName).isEqualTo("テストユーザー")
        assertThat(actual?.password).isEqualTo("test1")
    }

    @Test
    @DisplayName("ユーザー情報を更新できる")
    fun updateUserTest() {
        // db test data setup
        val insertTestUser = insertInto("m_user") {
            withDefaultValue("regdate", LocalDateTime.now())
            withDefaultValue("upddate", LocalDateTime.now())
            withDefaultValue("version", 1)
            columns("userid", "username", "password", "regid", "updid")
            values("test1", "テストユーザー", "test1", "test1", "test1")
        }

        val operation = sequenceOf(deleteAllUserTable, insertTestUser)
        DbSetup(destination, operation).launch()

        val updateUser =
            UserEntity(userId = "test1", userName = "テストユーザー2", password = "updated", regId = "test1", updId = "test1")
        val update = userDao.updateUser(updateUser)

        val actual = update.entity

        assertThat(actual?.userName).isEqualTo("テストユーザー2")
        assertThat(actual?.password).isEqualTo("updated")
    }
}