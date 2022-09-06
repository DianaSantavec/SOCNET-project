#!/bin/bash

# grep 'Vertices:' test_output.txt | sed 's/^.*: //' 
# grep -o 'Vertices:.*' test_output.txt | cut -d ',' -f 
grep -o 'Vertices:.*' output.txt | sed 's/[^,]//g' | awk '{ print length }' > output_sizes
sort -nr output_sizes
