
FROM mcr.microsoft.com/mssql/server:2022-latest

ENV ACCEPT_EULA=Y
ENV MSSQL_PID: "Developer"
ENV MSSQL_SA_PASSWORD=${SA_PASSWORD}

EXPOSE 1433

# Create directories
RUN mkdir -p /var/opt/mssql/backup

# Copy database backup and restore script
COPY data/wgapp.bak /var/opt/mssql/backup/

# Start SQL Server and restore the database in a single command
CMD /opt/mssql/bin/sqlservr & \
    sleep 10s && \
    /opt/mssql-tools18/bin/sqlcmd -S localhost -U SA -P "${SA_PASSWORD}" -Q "RESTORE FILELISTONLY FROM DISK='/var/opt/mssql/backup/wgapp.bak'" -C && \
    /opt/mssql-tools18/bin/sqlcmd -S localhost -U SA -P "${SA_PASSWORD}" -Q "RESTORE DATABASE WorksheetGeneratorApp FROM DISK='/var/opt/mssql/backup/wgapp.bak' WITH MOVE 'WorksheetGeneratorApp' TO '/var/opt/mssql/data/WorksheetGeneratorApp.mdf', MOVE 'WorksheetGeneratorApp_log' TO '/var/opt/mssql/data/WorksheetGeneratorApp_log.ldf'" -C && \
    tail -f /dev/null

