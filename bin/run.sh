#!/bin/bash

export mem="256M"

if [ $# -eq 1 ]; then
  export problem="$1"
  export project_dir=`dirname $0`"/../"
  export data_dir=${project_dir}/bin/${problem}
  export class_path="${project_dir}/build/"
  export class=`find ${project_dir}/src/ -type d -name "*${problem}*" | awk 'BEGIN{FS="/"}{for (i=1;i<=NF;i++) {if (c) {class=class $i "."} if ($i == "src") {c=1}}}END{print class "Solution"}'`

  if [ -d ${data_dir} ]; then
    for input in `ls ${data_dir}/input*`; do
      export expected_output=`echo ${input} | awk 'BEGIN{FS="input"}{for (i=1;i<NF;i++) {output=output $i} print output "output" $NF}'`
      export generated_output="${expected_output}.gen"
      if [ -e ${expected_output} ]; then
        echo -en "Running ${problem} on testcase ${input}\t"
        java -Xmx${mem} -cp ${class_path} ${class} < ${input} >${generated_output}
        diff ${expected_output} ${generated_output} > ${expected_output}.diff
        if [ -s ${expected_output}.diff ]; then
          # failed
          tput setaf 1 2>/dev/null
          echo "[FAILED]"
          tput sgr0 2>/dev/null
          echo "Output diff: "
          cat ${expected_output}
        else
          # passed
          tput setaf 4 2>/dev/null
          echo "[PASSED]"
          tput sgr0 2>/dev/null
        fi
      fi
    done
  fi


else
  echo "Usage: $0 problem"
fi
