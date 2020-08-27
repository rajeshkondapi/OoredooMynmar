set projectLocation=C:\Users\qtpuser\Downloads\appiumooredp\AppiumOoredoo
cd %projectLocation%
set classpath=%projectLocation%\bin;%projectLocation%\lib\*
java org.testng.TestNG %projectLocation%\testng.xml
pause