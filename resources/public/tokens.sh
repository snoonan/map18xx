#!/bin/bash

while read name upper lower upper_band lower_band; do
    # format is upper [lower [upper_band [lower_band]]]
    # defaults are:
    #   one band specified: lower band is same as upper band
    #   no bands specified: band is same as half (upper_band = upper, lower_band = lower)
    #   no lower, everything is same as upper.
    lower=${lower:-$upper}
    lower_band=${lower_band:-$upper_band}
    lower_band=${lower_band:-$lower}
    upper_band=${upper_band:-$upper}

    html_name=${name/&/\\\&amp;}
    file_name=${name/&/_}
    sed -e "s/\$upper_band/$upper_band/g" \
        -e "s/\$lower_band/$lower_band/g" \
        -e "s/\$upper/$upper/g" \
        -e "s/\$lower/$lower/g" \
        -e "s/\$name/$html_name/g" \
        template.svg >$file_name.svg
done
