package es.ulpgc.eite.da.quiz.question;

import android.util.Log;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.da.quiz.app.AppMediator;
import es.ulpgc.eite.da.quiz.app.CheatToQuestionState;
import es.ulpgc.eite.da.quiz.app.QuestionToCheatState;

public class QuestionPresenter implements QuestionContract.Presenter {

  public static String TAG = QuestionPresenter.class.getSimpleName();

  private AppMediator mediator;
  private WeakReference<QuestionContract.View> view;
  private QuestionState state;
  private QuestionContract.Model model;

  public QuestionPresenter(AppMediator mediator) {
    this.mediator = mediator;
    state = mediator.getQuestionState();
  }

  @Override
  public void onStart() {
    Log.e(TAG, "onStart()");

    // call the model
    state.question = model.getQuestion();
    state.option1 = model.getOption1();
    state.option2 = model.getOption2();
    state.option3 = model.getOption3();

    // reset state to tests
    state.answerCheated=false;
    state.optionClicked = false;
    state.option = 0;

    // update the view
    disableNextButton();
    view.get().resetReply();
  }


  @Override
  public void onRestart() {
    Log.e(TAG, "onRestart()");

      if(state.optionClicked){
        view.get().updateReply(!state.cheatEnabled);
      }else {
        view.get().resetReply();
      }
      view.get().displayQuestion(state);


    //TODO: falta implementacion

  }


  @Override
  public void onResume() {
    Log.e(TAG, "onResume()");

    //TODO: falta implementacion

    // use passed state if is necessary
    CheatToQuestionState savedState = getStateFromCheatScreen();
    if (savedState != null) {

      state.answerCheated=savedState.answerCheated;
      if(state.answerCheated){
        onNextButtonClicked();
      }
    }

    // update the view
    view.get().displayQuestion(state);
  }


  @Override
  public void onDestroy() {
    Log.e(TAG, "onDestroy()");
  }

  @Override
  public void onOptionButtonClicked(int option) {
    Log.e(TAG, "onOptionButtonClicked()");

    //TODO: falta implementacion

    boolean isCorrect = model.isCorrectOption(option);
    state.optionClicked=true;
    state.option=option;

    if(isCorrect) {

      state.cheatEnabled=false;
      state.optionEnabled=false;
      state.answerCheated=true;

    } else {
      state.cheatEnabled=true;
      state.optionEnabled=false;
      state.answerCheated=true;

    }
    this.enableNextButton();
    view.get().updateReply(isCorrect);
    view.get().displayQuestion(state);

  }

  @Override
  public void onNextButtonClicked() {
    Log.e(TAG, "onNextButtonClicked()");
    model.updateQuizIndex();
    state.question=model.getQuestion();
    state.option1=model.getOption1();
    state.option2=model.getOption2();
    state.option3=model.getOption3();
    view.get().resetReply();
    disableNextButton();
    view.get().displayQuestion(state);





    //TODO: falta implementacion
  }

  @Override
  public void onCheatButtonClicked() {
    Log.e(TAG, "onCheatButtonClicked()");
    String pasarCheat=model.getAnswer();
    QuestionToCheatState estado_Cheat= new QuestionToCheatState(pasarCheat);
    passStateToCheatScreen(estado_Cheat);
    view.get().navigateToCheatScreen();

    //TODO: falta implementacion
  }

  private void passStateToCheatScreen(QuestionToCheatState state) {
    mediator.setQuestionToCheatState(state);

    //TODO: falta implementacion

  }

  private CheatToQuestionState getStateFromCheatScreen() {
    CheatToQuestionState estadoCheat=mediator.getCheatToQuestionState();
    return estadoCheat;

    //TODO: falta implementacion


  }

  private void disableNextButton() {
    state.optionEnabled=true;
    state.cheatEnabled=true;
    state.nextEnabled=false;

  }

  private void enableNextButton() {
    state.optionEnabled=false;

    if(!model.hasQuizFinished()) {
      state.nextEnabled=true;
    }
  }

  @Override
  public void injectView(WeakReference<QuestionContract.View> view) {
    this.view = view;
  }

  @Override
  public void injectModel(QuestionContract.Model model) {
    this.model = model;
  }

}
