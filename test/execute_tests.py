### MAKE SURE TO CHANGE THE PHASE OF TESTING
PHASE = "phase_3"
### MAKE SURE TO RUN IN REPO ROOT DIRECTORY




import platform
import os
import subprocess
from pathlib import Path

  
ROOT_DIR = Path(__file__).parent.parent

operatingsys = platform.system()

os.chdir(ROOT_DIR)

if (operatingsys == "Windows"):
    file_path = os.path.join(os.getcwd(), "test", PHASE.lower(), "TEST_windows.txt")
else:
    file_path = os.path.join(os.getcwd(), "test", PHASE.lower(), "TEST_nonwindows.txt")


with open (file_path, "r") as handle:
    commands = handle.read()
 
commands = '\n'.join([i for i in commands.split('\n') if len(i) > 0])
commands = ";".join(commands.strip().splitlines())
 
if operatingsys == "Windows":
    # windows is really weird
    subprocess.run(['powershell.exe',"-Command", commands], shell = True)
else:
    subprocess.run(commands, shell = True)