package com.muscletracking.mtapi.entity

import org.seasar.doma.*
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.LocalDateTime.now

@Entity(immutable = true)
@Table(name = "m_user")
data class User(
    @Id
    @Column(name = "userid")
    val id: String = "",

    @Column(name = "username")
    val userName: String = "",

    @Column(name = "password")
    val password: String = "",

    @Column(name = "regid")
    val regId: String = "",

    @Column(name = "regdate")
    val regDate: LocalDateTime = now(),

    @Column(name = "updid")
    val updId: String = "",

    @Column(name = "upddate")
    val updDate: LocalDateTime = now(),

    @Version
    @Column(name = "version")
    val version: Int = 1,
)