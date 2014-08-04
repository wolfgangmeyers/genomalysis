; Script generated by the Inno Setup Script Wizard.
; SEE THE DOCUMENTATION FOR DETAILS ON CREATING INNO SETUP SCRIPT FILES!

[Setup]
; NOTE: The value of AppId uniquely identifies this application.
; Do not use the same AppId value in installers for other applications.
; (To generate a new GUID, click Tools | Generate GUID inside the IDE.)
AppId={{E86DA481-A605-4E38-BC9A-D37E5DC38916}
AppName=Genomalysis
AppVersion=1.5
;AppVerName=Genomalysis 1.5
AppPublisher=Genomalysis
AppPublisherURL=http://www.genomalysis.org
AppSupportURL=http://www.genomalysis.org
AppUpdatesURL=http://www.genomalysis.org
DefaultDirName=C:\Genomalysis
DefaultGroupName=Genomalysis
OutputBaseFilename=genomalysis-setup
Compression=lzma
SolidCompression=yes

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"

[Files]
Source: "D:\java-workspace\Genomalysis\Genomalysis\target\universal\stage\*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs
Source: "D:\java-workspace\Genomalysis\dna-icon.ico"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs

; NOTE: Don't use "Flags: ignoreversion" on any shared system files

[Icons]
Name: "{group}\Genomalysis"; Filename: "{app}\bin\genomalysis.bat"; IconFilename: "{app}\dna-icon.ico"
Name: "{commondesktop}\Genomalysis"; Filename: "{app}\bin\genomalysis.bat"; IconFilename: "{app}\dna-icon.ico"

[Run]
Filename: "{app}\bin\genomalysis.bat"; Description: "{cm:LaunchProgram,{#StringChange('Genomalysis', '&', '&&')}}"; Flags: nowait postinstall skipifsilent

