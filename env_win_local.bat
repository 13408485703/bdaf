@echo off
color 0a
echo.------------------------------------
echo.TODO:����java��������
echo.Author:Zhangfan
echo.Feedback E-mail:13408485703@163.com
echo.
echo.------------------------------------
::����еĻ�����ɾ��JAVA_HOME
wmic ENVIRONMENT where "name='JAVA_HOME'" delete
::����еĻ�����ɾ��ClASSPATH 
wmic ENVIRONMENT where "name='CLASSPATH'" delete
::����JAVA_HOME
wmic ENVIRONMENT create name="JAVA_HOME",username="<system>",VariableValue="%~dp0jre7"
::����PATH�й���java�Ļ�������
wmic ENVIRONMENT where "name='path' and username='<system>'" set VariableValue="%path%;%~dp0jre7\bin;"
::����CLASSPATH
wmic ENVIRONMENT create name="CLASSPATH",username="<system>",VariableValue=".;%~dp0jre7\lib\rt.jar;"
pause