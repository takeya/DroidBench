__author__ = 'Pantay'

"""
Converts the DroidBench projects to a gradle compatible project structure
"""
import sys
from os import path, listdir, rmdir, makedirs
import shutil


droidBenchPath = ''
projectsPath = ''
convertPath = ''


def main():
    if not check_droidbench_directory():
        print('Not a valid DroidBench')
        return
    copy_project_paths()
    create_main_build_file()


def check_droidbench_directory():
    if path.exists(droidBenchPath):
        for file in listdir(droidBenchPath):
            print('file: ' + file)
            if file == 'eclipse-project':
                print('DroidBench seems legit.')
                return True
    return False


def copy_project_paths():
    global convertPath
    global projectsPath
    projectsPath = path.join(droidBenchPath, 'eclipse-project')
    for filename in listdir(projectsPath):
        shutil.copytree(path.join(projectsPath, filename), path.join(convertPath, filename))
        print('Moved Project ' + filename + ' to new folder.')
    projectsPath = convertPath
    print('projectPath = ' + projectsPath)
    for category in listdir(projectsPath):
        copy_sub_projects(category)


def copy_sub_projects(category):
    global convertPath
    global projectsPath
    categoryFolder = path.join(projectsPath, category)
    if not path.isdir(categoryFolder) or category[0] == '.' or category == 'gen' or category == 'out':
        # not a folder
        return
    print('\nMain categories: ' + category)
    settingsGradleFile = open(path.join(convertPath, 'settings.gradle'), 'a')
    for subcategory in listdir(categoryFolder):
        settingsGradleFile.write("include ':" + category + ":" + subcategory + "'\n")
        print('\t-- ' + subcategory)
        transform_project(path.join(categoryFolder, subcategory))
    settingsGradleFile.close()


def transform_project(projectFolder):
    srcPath = path.join(projectFolder, 'src')
    if not path.exists(srcPath):
        makedirs(srcPath)
    mainPath = path.join(srcPath, 'main')
    if not path.exists(mainPath):
        makedirs(mainPath)
    javaPath = path.join(mainPath, 'java')
    if not path.exists(javaPath):
        makedirs(javaPath)

    if path.exists(path.join(projectFolder, 'res')):
        print('Move res into src/main folder')
        shutil.move(path.join(projectFolder, 'res'), mainPath)

    if path.exists(path.join(projectFolder, 'libs')):
        shutil.rmtree(path.join(projectFolder, 'libs'))

    if path.exists(path.join(projectFolder, 'AndroidManifest.xml')):
        print('Move AndroidManifest.xml into src/main folder')
        shutil.move(path.join(projectFolder, 'AndroidManifest.xml'), mainPath)

    # move all content from src to app/src/main/java
    if path.exists(path.join(projectFolder, 'src')):
        print('Move src content into app/src/main/java folder')
        for filename in listdir(path.join(projectFolder, 'src')):
            if not filename == 'main':
                shutil.move(path.join(projectFolder, 'src', filename), javaPath)

    create_app_build_file(projectFolder)


def create_app_build_file(dst):
    buildFile = open(path.join(dst, 'build.gradle'), 'w')
    buildFile.write("apply plugin: 'com.android.application'\n"
                    "\nandroid {"
                    "\n\tcompileSdkVersion 22"
                    '\n\tbuildToolsVersion "22.0.1"'
                    "\n\tbuildTypes {"
                    "\n\t\trelease {"
                    "\n\t\t\tminifyEnabled false"
                    "\n\t\t\tproguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'"
                    "\n\t\t}"
                    "\n\t}"
                    "\n\tlintOptions {"
                    "\n\t\tabortOnError false"
                    "\n\t}"
                    "\n}\n"
                    "\ndependencies {"
                    "\n\tcompile fileTree(dir: 'libs', include: ['*.jar'])"
                    "\n\tcompile 'com.android.support:appcompat-v7:22.1.1'"
                    "\n}")
    buildFile.close()


def create_main_build_file():
    global convertPath
    buildFile = open(path.join(convertPath, 'build.gradle'), 'w')
    buildFile.write("buildscript {"
                    "\n\trepositories {"
                    '\n\t\tjcenter()'
                    "\n\t}"
                    "\n\tdependencies {"
                    "\n\t\tclasspath 'com.android.tools.build:gradle:1.2.2'"
                    "\n\t}"
                    "\n}\n"
                    "\nallprojects {"
                    "\n\trepositories {"
                    "\n\t\tjcenter()"
                    "\n\t}"
                    "\n}")
    buildFile.close()


if __name__ == '__main__':
    if len(sys.argv) < 3:
        print('usage: "droidBenchConverter.py /path/to/DroidBench/ /path/to/new/convert/place"')
    else:
        droidBenchPath = sys.argv[1]
        convertPath = sys.argv[2]
        main()