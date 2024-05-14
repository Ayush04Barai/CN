set ns [ new Simulator ]

$ns color 1 Blue
$ns color 2 Red

set nf [open simple.nam w]
$ns namtrace-all $nf

set tracefile [open simple.tr w]
$ns trace-all $tracefile

proc finish {} {

        global ns nf
        $ns flush-trace
        #Close the NAM trace file
        close $nf
        #Execute NAM on the trace file
        exec nam out.nam &
        exit 0
}

set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]
set n3 [$ns node]
set n4 [$ns node]

$ns duplex-link $n1 $n0 3Mb 10ms DropTail
$ns duplex-link $n2 $n0 3Mb 10ms DropTail
$ns duplex-link $n3 $n0 3Mb 10ms DropTail
$ns duplex-link $n4 $n0 3Mb 10ms DropTail

$ns queue-limit $n1 $n0 10
$ns queue-limit $n2 $n0 10
$ns queue-limit $n3 $n0 10
$ns queue-limit $n4 $n0 10
#position
$ns duplex-link-op $n1 $n0 orient left-up
$ns duplex-link-op $n2 $n0 orient left-down
$ns duplex-link-op $n3 $n0 orient right-up
$ns duplex-link-op $n4 $n0 orient right-down
#n1 to n4
set tcp [new Agent/TCP]
$tcp set class_ 2
$ns attach-agent $n1 $tcp
set sink [new Agent/TCPSink]
$ns attach-agent $n4 $sink
$ns connect $tcp $sink
$tcp set fid_ 1
#n2 to n3
set udp [new Agent/UDP]
$ns attach-agent $n2 $udp
set null [new Agent/Null]
$ns attach-agent $n3 $null
$ns connect $udp $null
$udp set fid_ 2
#ftp
set ftp [new Application/FTP]
$ftp attach-agent $tcp
$ftp set type_ FTP

#cbr
set cbr [new Application/Traffic/CBR]
$cbr attach-agent $udp
$cbr set type_ CBR
$cbr set packet_size_ 1000
$cbr set rate_ 1mb
$cbr set random_ false

$ns at 0.1 "$cbr start"
$ns at 1.0 "$ftp start"
$ns at 4.0 "$ftp stop"
$ns at 4.5 "$cbr stop"

$ns at 5.0 "finish"

puts "CBR packet size = [$cbr set packet_size_]"
puts "CBR interval = [$cbr set interval_]"

$ns run