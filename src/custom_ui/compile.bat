@echo off
cd /d "C:\Users\Ugochukwu Nwodo\Documents\NetBeansProjects\InventoryManagementSystemV_01\src"

javac custom_ui\RoundedPanel.java custom_ui\RoundedPanelBeanInfo.java custom_ui\CustomTitleBar.java custom_ui\CustomTitleBarBeanInfo.java -d out
if %errorlevel% neq 0 (
    echo.
    echo Compilation failed. See errors above.
    pause
    exit /b 1
)

echo.
echo Packaging JAR...
jar cf custom_ui\RoundedPanel.jar -C out .

if %errorlevel% neq 0 (
    echo.
    echo JAR packaging failed.
    pause
    exit /b 1
)

echo.
echo Done. RoundedPanel.jar is ready.
pause