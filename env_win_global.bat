@echo off
color 0a
echo.------------------------------------
echo.TODO:设置java环境变量
echo.Author:Zhangfan
echo.Feedback E-mail:13408485703@163.com
echo.
echo.------------------------------------
::如果有的话，先删除JAVA_HOME
wmic ENVIRONMENT where "name='JAVA_HOME'" delete
::如果有的话，先删除ClASSPATH 
wmic ENVIRONMENT where "name='CLASSPATH'" delete
::创建JAVA_HOME
wmic ENVIRONMENT create name="JAVA_HOME",username="<system>",VariableValue="C:\Program Files (x86)\Java\jdk1.7.0_45"
::增加PATH中关于java的环境变量
wmic ENVIRONMENT where "name='path' and username='<system>'" set VariableValue="%path%;%JAVA_HOME%\bin;"
::创建CLASSPATH
wmic ENVIRONMENT create name="CLASSPATH",username="<system>",VariableValue=".;%JAVA_HOME%\jre\lib\rt.jar;"
pause