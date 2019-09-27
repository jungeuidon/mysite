package kr.co.itcen.mysite.action.board;

import kr.co.itcen.web.mvc.Action;
import kr.co.itcen.web.mvc.ActionFactory;

public class BoardActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		if("writeform".equals(actionName)) {
			action = new WriteFormAction();
		} else if("write".equals(actionName)) {
			action = new WriteAction();
		} else if("view".equals(actionName)) {
			action = new ViewAction();
		} else if("delete".equals(actionName)) {
//			System.out.println("del");
			action = new DeleteAction();
		} else if("modifyform".equals(actionName)) {
			System.out.println("modifyform접속");
			action = new ModifyFormAction();
		} else if("modify".equals(actionName)) {
			System.out.println("modify접속");
			action = new ModifyAction();
		} else {
			// default(list)
			action = new ListAction();
		}
		return action;
	}
}
