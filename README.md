### PR Cloner

A simple tool for cloning git pull request.

##### Prerequisite

* Git for Windows
* A git project

##### Usage

1. Run the **pr-cloner.exe**.

2. Expect to the following window:
	 	![Application Window](main-application-window.png)
	
3. Fill-up the **Git Project Directory** field to have the git project.

     > You can **double click** the **Git Project Directory field** to use the **directory explorer** or use the **File -> Git Project Dir** menu.

4. Expect to load all the **remotes attached to the git project** in to the dropdown of the **remote field**.

5. Select the remote that is **deemed to be the upstream**.

6. On the **pull request field** typed in the **pull request number** from the upstream.

7. Click the **clone button** to clone the pull request.

     > If everything is successful, open your **git client** and look for the **local branch name** that has the format **PR<pull request number>**.  You can switch to that branch to review it or do testing on it.

8. Use the **close button** or the **x** at the upper right corner of the window close it.

##### [Changelog](CHANGELOG.md)

