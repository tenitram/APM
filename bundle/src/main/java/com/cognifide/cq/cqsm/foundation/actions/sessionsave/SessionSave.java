/*-
 * ========================LICENSE_START=================================
 * AEM Permission Management
 * %%
 * Copyright (C) 2013 Cognifide Limited
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * =========================LICENSE_END==================================
 */
package com.cognifide.cq.cqsm.foundation.actions.sessionsave;

import com.cognifide.cq.cqsm.api.actions.Action;
import com.cognifide.cq.cqsm.api.actions.ActionResult;
import com.cognifide.cq.cqsm.api.executors.Context;
import com.cognifide.cq.cqsm.core.sessions.SessionSavingMode;
import com.cognifide.cq.cqsm.core.sessions.SessionSavingPolicy;
import com.cognifide.cq.cqsm.core.utils.MessagingUtils;

public class SessionSave implements Action {

	private final String mode;

	public SessionSave(final String mode) {
		this.mode = mode;
	}

	@Override
	public ActionResult simulate(Context context) {
		return process(context, false);
	}

	@Override
	public ActionResult execute(final Context context) {
		return process(context, true);
	}

	private ActionResult process(final Context context, boolean execute) {
		ActionResult actionResult = new ActionResult();
		if (execute) {
			try {
				SessionSavingMode savingMode = SessionSavingMode.valueOfMode(mode);
				SessionSavingPolicy savingPolicy = context.getSavingPolicy();
				savingPolicy.setMode(savingMode);
				actionResult.logMessage("Session saving mode set to: " + mode);
			} catch (IllegalArgumentException e) {
				actionResult.logError(MessagingUtils.createMessage(e));
			}
		}
		return actionResult;
	}

	@Override
	public boolean isGeneric() {
		return true;
	}
}
