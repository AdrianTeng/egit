package org.eclipse.egit.ui.internal.console;

import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.RebaseResult;
import org.eclipse.jgit.transport.FetchResult;
import org.eclipse.jgit.transport.TrackingRefUpdate;
import org.eclipse.ui.console.IOConsole;
import org.eclipse.ui.console.IOConsoleOutputStream;

/**
 *
 * @author Adrian Teng
 *
 */
public class GitConsole extends IOConsole {
	/**
	 * Type identifier
	 */
	public static final String GIT_CONSOLE_TYPE = "org.eclipse.egit.ui.internal.console.GitConsole"; //$NON-NLS-1$

	private static final String newline = System.getProperty("line.separator"); //$NON-NLS-1$

	/**
	 * Construct Git Console
	 */
	public GitConsole() {
		// TODO: Refactor the strings out
		// TODO:
		super("Git", null, null); //$NON-NLS-1$
		this.setType(GIT_CONSOLE_TYPE);
		IOConsoleOutputStream stream = this.newOutputStream();
		try {
			stream.write("Hello World!"); //$NON-NLS-1$
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Write a message to the console. Timestamp are automatically prepended.
	 *
	 * @param message
	 */
	public void write(String message) {
		IOConsoleOutputStream stream = this.newOutputStream();
		// TODO: Time formatting read from user pref iff it exist?
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") //$NON-NLS-1$
				.format(Calendar.getInstance().getTime());
		try {
			stream.write(String
					.format("%s - %s%s", timeStamp, message, newline)); //$NON-NLS-1$
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				stream.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * @param pullResult
	 */
	public void write(PullResult pullResult) {
		// TODO: split pullResult into fetch / merge / rebase?
		if (pullResult.getFetchResult().getTrackingRefUpdates().isEmpty()) {
			this.write(MessageFormat.format(
					UIText.FetchResultDialog_labelEmptyResult,
					pullResult.getFetchedFrom()));
		} else {
			for (TrackingRefUpdate update : pullResult.getFetchResult()
					.getTrackingRefUpdates()) {
				// TODO: remoteName and localName seems to be other way around.
				// Bug?
				this.write(String.format(
						"Fetch: [%s] %s -> %s", update.getResult(), //$NON-NLS-1$
						update.getRemoteName(), update.getLocalName()));
			}
		}
		String messageFromServer = pullResult.getFetchResult().getMessages();
		if (messageFromServer != null && messageFromServer.length() != 0) {
			this.write(messageFromServer);
		}

		MergeResult mergeResult = pullResult.getMergeResult();
		if (mergeResult != null) {
			this.write(String.format(
					"Merged %d commits using %s", //$NON-NLS-1$
					mergeResult.getMergedCommits().length,
					mergeResult.getMergeStatus()));
		}

		// this.write(pullResult.getFetchResult());
		// this.write(pullResult.getMergeResult());
		// this.write(pullResult.getRebaseResult());
	}

	/**
	 * @param fetchResult
	 */
	public void write(FetchResult fetchResult) {
		if (fetchResult.getTrackingRefUpdates().isEmpty()) {
			this.write(String.format(UIText.FetchResultDialog_labelEmptyResult));
		}
		String messageFromServer = fetchResult.getMessages();
		if (messageFromServer != null) {
			this.write(messageFromServer);
		}
	}

	/**
	 * @param mergeResult
	 */
	public void write(MergeResult mergeResult) {
		// this.write(mergeResult.toString());
	}

	/**
	 * @param rebaseResult
	 */
	public void write(RebaseResult rebaseResult) {
		// this.write(rebaseResult.toString());
	}



}
