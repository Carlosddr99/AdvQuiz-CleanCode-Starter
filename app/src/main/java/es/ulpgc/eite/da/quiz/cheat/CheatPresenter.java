package es.ulpgc.eite.da.quiz.cheat;

import android.util.Log;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.da.quiz.app.AppMediator;
import es.ulpgc.eite.da.quiz.app.CheatToQuestionState;
import es.ulpgc.eite.da.quiz.app.QuestionToCheatState;

public class CheatPresenter implements CheatContract.Presenter {

  public static String TAG = CheatPresenter.class.getSimpleName();

  private AppMediator mediator;
  private WeakReference<CheatContract.View> view;
  private CheatState state;
  private CheatContract.Model model;

  public CheatPresenter(CheatState state) {
    this.state = state;
  }

  public CheatPresenter(AppMediator mediator) {
    this.mediator = mediator;
    state = mediator.getCheatState();
  }


  @Override
  public void onStart() {
    Log.e(TAG, "onStart()");

    // reset state to tests
    state.answerEnabled=true;
    state.answerCheated=false;
    state.answer = null;
    state.answer="???";

    // update the view
    view.get().resetAnswer();

  }

  @Override
  public void onRestart() {
    Log.e(TAG, "onRestart()");
    if(state.answerCheated){
      view.get().displayAnswer(state);
    }

    //TODO: falta implementacion
  }

  @Override
  public void onResume() {
    Log.e(TAG, "onResume()");

    //TODO: falta implementacion

    // use passed state if is necessary
    QuestionToCheatState savedState = getStateFromQuestionScreen();

    if (savedState != null) {

      // fetch the model
    model.setAnswer(savedState.answer);
      // update the state

    }

    // update the view

    view.get().displayAnswer(state);

  }

  @Override
  public void onDestroy() {
    Log.e(TAG, "onDestroy()");
  }

  @Override
  public void onBackPressed() {
    Log.e(TAG, "onBackPressed()");
    CheatToQuestionState pasarQuestion=new CheatToQuestionState(state.answerCheated);
    passStateToQuestionScreen(pasarQuestion);
    view.get().onFinish();

    //TODO: falta implementacion

  }

  @Override
  public void onWarningButtonClicked(int option) {
    Log.e(TAG, "onWarningButtonClicked()");

    //TODO: falta implementacion
    //option=1 => yes, option=0 => no
    if(option==1){
      state.answerCheated=true;
      state.answerEnabled=false;
      state.answer=model.getAnswer();
      view.get().displayAnswer(state);
    }else{
      view.get().onFinish();
    }

  }

  private void passStateToQuestionScreen(CheatToQuestionState state) {

    CheatToQuestionState pasarQuestion=state;
            mediator.setCheatToQuestionState(pasarQuestion);

    //TODO: falta implementacion
  }

  private QuestionToCheatState getStateFromQuestionScreen() {
   return  mediator.getQuestionToCheatState();

    //TODO: falta implementacion

  }

  @Override
  public void injectView(WeakReference<CheatContract.View> view) {
    this.view = view;
  }

  @Override
  public void injectModel(CheatContract.Model model) {
    this.model = model;
  }

}
