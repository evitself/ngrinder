/*
 * Copyright (C) 2012 - 2012 NHN Corporation
 * All rights reserved.
 *
 * This file is part of The nGrinder software distribution. Refer to
 * the file LICENSE which is part of The nGrinder distribution for
 * licensing details. The nGrinder distribution is available on the
 * Internet at http://nhnopensource.org/ngrinder
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT HOLDERS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.ngrinder.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Performance Test Status.
 * 
 * @author JunHo Yoon
 * @since 3.0
 */
public enum Status {
	/** Just Saved.. not ready to run */
	SAVED(StatusCategory.PREPARE),
	/** test ready. */
	READY(StatusCategory.PREPARE),
	/** Just before starting console. */
	START_CONSOLE(StatusCategory.PROGRESSING),
	/** Just after staring console. */
	START_CONSOLE_FINISHED(StatusCategory.PROGRESSING),
	/** Just before starting agents. */
	START_AGENTS(StatusCategory.PROGRESSING),
	/** Just after starting agents. */
	START_AGENTS_FINISHED(StatusCategory.PROGRESSING),
	/** Just before distributing files. */
	DISTRIBUTE_FILES(StatusCategory.PROGRESSING),
	/** Just after distributing files. */
	DISTRIBUTE_FILES_FINISHED(StatusCategory.PROGRESSING),
	/** Just before staring testing. */
	START_TESTING(StatusCategory.TESTING),
	/** Just after staring testing. */
	TESTING(StatusCategory.TESTING),
	/** Waiting for test is finishing. */
	TESTING_FINISHED(StatusCategory.TESTING),
	/** Detected Abnormal testing. */
	ABNORMAL_TESTING(StatusCategory.TESTING),
	/** Test finished. */
	FINISHED(StatusCategory.FINISHED),
	/** Stopped by error. */
	STOP_ON_ERROR(StatusCategory.STOP),
	/** Test cancel. */
	CANCELED(StatusCategory.STOP),
	/** Nothing. */
	UNKNOWN(StatusCategory.STOP);

	private final StatusCategory category;

	/**
	 * Constructor.
	 * 
	 * @param category
	 *            category of this status within.
	 */
	Status(StatusCategory category) {
		this.category = category;
	}

	public StatusCategory getCategory() {
		return category;
	}

	public boolean isStoppable() {
		return category.isStoppable();
	}

	public boolean isDeletable() {
		return category.isDeletable();
	}

	public String getIconName() {
		return category.getIconName();
	}

	public static Status[] getProcessingOrTestingTestStatus() {
		List<Status> status = new ArrayList<Status>();
		for (Status each : values()) {
			if (isWorkingStatus(each)) {
				status.add(each);
			}
		}
		return status.toArray(new Status[0]);
	}

	private static boolean isWorkingStatus(Status status) {
		return status.getCategory() == StatusCategory.PROGRESSING || status.getCategory() == StatusCategory.TESTING;
	}

	public static Status[] getTestingTestStates() {
		List<Status> status = new ArrayList<Status>();
		for (Status each : values()) {
			if (each.getCategory() == StatusCategory.TESTING) {
				status.add(each);
			}
		}
		return status.toArray(new Status[0]);
	}

	public String getSpringMessageKey() {
		return "perftest.status." + name().toLowerCase();
	}
}
