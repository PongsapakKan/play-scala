package models

import models.WorkStatus.WorkStatus
import play.api.libs.json.{JsValue, Json, Writes}

/**
  * Created by LenovoPC on 2017/06/13.
  */

object WorkStatus extends Enumeration {
  type WorkStatus = Value
  val Pending = Value(1)
  val Done = Value(2)
}

case class Subject(id: Int = 0, name: String, content: String, status: WorkStatus)

object Subject {
  implicit val subjectWrite = new Writes[Subject] {
    override def writes(subject: Subject): JsValue = {
      Json.obj(
        "id" -> subject.id,
        "name" -> subject.name,
        "content" -> subject.content,
        "status" -> subject.status.id
      )
    }
  }
}

case class SubjectInputForm(name: String, content: String, status: Byte)

case class WorkStatusInput(status: Byte)
