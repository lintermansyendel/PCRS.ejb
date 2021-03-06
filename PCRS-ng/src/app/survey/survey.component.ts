import { Component, OnInit } from '@angular/core';
import { EnergyOrInterestOption } from '../model/energy-or-interest-option.enum';
import { SurveyService } from '../service/survey.service';
import { ISurvey } from '../model/Interfaces/ISurvey';
import { SurveyKind } from '../model/survey-kind.enum';
import { UserService } from '../service/user.service';
import { Router } from '@angular/router';
import { IAlert } from '../model/Interfaces/IAlert';
import { AlertType } from '../model/alert-type.enum';

@Component({
  selector: 'app-survey',
  templateUrl: './survey.component.html',
  styleUrls: ['./survey.component.css']
})
export class SurveyComponent implements OnInit {
  survey$: ISurvey;
  private errors: IAlert[];

  constructor(public surveyService: SurveyService, private userService: UserService, private router: Router) {}

  ngOnInit() {
    this.errors = [];
    if (this.userService.user == null) {
      this.router.navigate(['login']);
    } else {
      this.surveyService.getSurveyForUser(this.userService.user, SurveyKind.TeamMember).subscribe(
        x => {
          this.survey$ = x.body;
        },
        (error) => {
          switch (error.status) {
            case 404: {
              this.errors.push({message: error.error, type: AlertType.DANGER});
              break;
            }
            default: {
              this.errors.push({message: error.error, type: AlertType.DANGER});
              break;
            }
          }
        }
      );
    }
  }

  finished(): boolean {
    let finished = true;
    for (const surveySection of this.survey$.surveySections) {
      if (finished) {
        for (const rating of surveySection.ratings) {
          if (finished) {
            if (rating.level == null) {
              finished = false;
            }
            if (surveySection.surveySectionDefinition.surveySectionDefinitionBo.surveySectionStrategy.energyRated) {
              if (rating.energy == null) {
                finished = false;
              }
            }
            if (surveySection.surveySectionDefinition.surveySectionDefinitionBo.surveySectionStrategy.interestRated) {
              if (rating.interest == null) {
                finished = false;
              }
            }
          }
        }
      }
    }
    return finished;
  }

  submitSurvey() {
    this.survey$.dateCompleted = (new Date()).toISOString().substring(0, 10);
    this.saveSurvey();
    this.router.navigate(['survey']);
  }
  saveSurvey() {
    this.surveyService.save(this.survey$);
  }
}
