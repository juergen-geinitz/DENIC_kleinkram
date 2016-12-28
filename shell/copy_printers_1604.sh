#!/bin/sh
#
PSNAME="prints2"
PRINTSERVER=${PSNAME}.office.denic.de
CONFFILE="/etc/cups/printers.conf"
#
IFS='
'
#
LANG=C
export LANG
#
for printer in `lpstat -h ${PRINTSERVER}:631/version=1.1 -t|\
		grep device|\
		grep socket: |\
		awk '{ print $3 }'|\
		sed 's/:$//'`
do
    rprinter="${printer}-${PSNAME}"
    if grep  -q "<Printer $printer" $CONFFILE
    then
	echo "$rprinter already present - doing nothing"
    else
	echo "adding $rprinter"
	cat <<EoF >> $CONFFILE
<Printer ${rprinter}>
UUID urn:uuid:`uuidgen`
Info $rprinter
DeviceURI ipp://${PRINTSERVER}/printers/${printer}
State Idle
StateTime `date '+%s'`
Type 6
Accepting Yes
Shared No
JobSheets none none
QuotaPeriod 0
PageLimit 0
KLimit 0
OpPolicy default
ErrorPolicy retry-job
</Printer>
EoF
    fi
done
