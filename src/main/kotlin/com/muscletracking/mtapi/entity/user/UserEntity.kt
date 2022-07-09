package com.muscletracking.mtapi.entity.user

import com.muscletracking.mtapi.entity.BaseColumnEntity
import org.seasar.doma.*
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import javax.print.attribute.standard.JobOriginatingUserName

@Entity
@Table(name = "m_user")
class UserEntity() : BaseColumnEntity() {
    @Id
    @Column(name = "userid")
    var userId: String = ""

    @Column(name = "username")
    var userName: String = ""

    @Column(name = "password")
    var password: String = ""

    constructor(userId: String, userName: String, password: String) : this() {
        this.userId = userId
        this.userName = userName
        this.password = password
        this.auditUser = userId
        this.auditDateTime = LocalDateTime.now()
    }
}