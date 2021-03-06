package controllers

import javax.inject.Inject

import Repositories.SubjectRepository
import models.{SubjectInputForm, WorkStatusInput}
import play.api.data.Form
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import play.api.data.Forms._

/**
  * Created by LenovoPC on 2017/06/13.
  */


class SubjectController @Inject() (subjectRepository: SubjectRepository) extends Controller {

  def getSubjects = Action { implicit request =>
    Ok(Json.toJson(subjectRepository.getAll()))
  }

  def getSubjectById(id: Int) = Action { implicit request =>
    val subject = subjectRepository.getById(id)

    if (!subject.isDefined)
      NotFound
    else
      Ok(Json.toJson(subject))
  }

  val subjectForm: Form[SubjectInputForm] = Form {
    mapping(
        "name" -> text,
        "content" -> text,
        "status" -> byteNumber
    )(SubjectInputForm.apply)(SubjectInputForm.unapply)
  }

  def createSubject = Action { implicit request =>
    if (subjectForm.bindFromRequest.hasErrors){
      BadRequest("Invalid data")
    } else {
      val inputSubject = subjectForm.bindFromRequest.get
      Created(Json.toJson(subjectRepository.create(inputSubject)))
    }
  }

  def updateSubject(id: Int) = Action { implicit request =>
    if (subjectForm.bindFromRequest.hasErrors) {
      BadRequest("Invalid data")
    } else {
      val inputSubject = subjectForm.bindFromRequest.get
      val updatedSubject = subjectRepository.update(id, inputSubject)

      if (updatedSubject == null)
        NotFound
      else
        Ok(Json.toJson(updatedSubject))
    }
  }

  val statusForm: Form[WorkStatusInput] = Form {
    mapping(
      "status" -> byteNumber
    )(WorkStatusInput.apply)(WorkStatusInput.unapply)
  }

  def updateStatus(id: Int) = Action { implicit request =>
    if (statusForm.bindFromRequest.hasErrors) {
      BadRequest("Invalid status")
    } else {
      val inputSubject = statusForm.bindFromRequest.get
      val updatedSubject = subjectRepository.updateStatus(id, inputSubject)

      if (updatedSubject == null)
        NotFound
      else
        Ok(Json.toJson(updatedSubject))
    }
  }

  def deleteSubject(id: Int) = Action { implicit request =>
    val deleteSubject = subjectRepository.delete(id)
    if (!deleteSubject.isDefined)
      NotFound
    else
      Ok("Subject was delete")
  }
}
