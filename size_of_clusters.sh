#!/bin/bash

grep -o 'Vertices:.*' output.txt | sed 's/[^,]//g' | awk '{ print length }' > output_sizes
sort -nr output_sizes
