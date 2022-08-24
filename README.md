# PR Cloner

A simple tool for cloning git pull request.

## Prerequisite

* Git for Windows
* A git project

## Usage

1. Run the **pr-cloner.exe**.

2. Expect the following window:
    	![Application Window](main-application-window.png)

3. Fill-up the **Git Project Directory** field to have the git project.

     > You can **double click** the **Git Project Directory field** to use the **directory explorer** or use the **File -> Git Project Dir** menu.

4. Expect to load all the **remotes attached to the git project** in to the dropdown of the **remote field**.

5. Select the remote that is **deemed to be the upstream**.

6. On the **pull request field** typed in the **pull request number** from the upstream.

7. Click the **clone button** to clone the pull request.

     > If successful the Pull Request field will turn to green as follows:
     >
     > ![](cloning-successful.png)

     > If everything is successful, open your **git client** and look for the **local branch name** that has the format **PR<pull request number>**.  You can switch to that branch to review it or do testing on it.

8. Use the **close button** or the **x** at the upper right corner of the window close it.

## Changing the mode

The following are the supported modes:

| Repository Type                           | Mode      |
| ----------------------------------------- | --------- |
| GitHub Repository *(This is the default)* | github    |
| Bitbucket Repository                      | bitbucket |

1. **Open** the **directory location** of the **pr-cloner.exe**.

2. **Open** the **conf directory**.

3. Using an editor, **open** the **pr-cloner.properties file**.

4. **Update** the **repoType field** to the appropriate mode. 

   *Opting to use the bitbucket repository update the repoType field to bitbucket like the following:*

   ```properties
   repoType=bitbucket
   ```

5. **Save and Close** the **pr-cloner.properties file**.

## [Changelog](CHANGELOG.md)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

