package com.oe;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class OeDriver extends Configured implements Tool {
        @Override
        public int run(String[] args) throws Exception {

            if (args.length != 2) {
              System.out.printf(
                  "Usage: %s [generic options] <input dir> <output dir>\n", getClass()
                      .getSimpleName());
              ToolRunner.printGenericCommandUsage(System.out);
              return -1;
            }

            JobConf conf = new JobConf(getConf(), OeDriver.class);
            conf.setJobName(this.getClass().getName());

            FileInputFormat.setInputPaths(conf, new Path(args[0]));
            FileOutputFormat.setOutputPath(conf, new Path(args[1]));

            conf.setMapperClass(OeMapper.class);
            conf.setReducerClass(OeReducer.class);

            conf.setMapOutputKeyClass(Text.class);
            conf.setMapOutputValueClass(IntWritable.class);

            conf.setOutputKeyClass(Text.class);
            conf.setOutputValueClass(IntWritable.class);

            JobClient.runJob(conf);
            return 0;
          }

          public static void main(String[] args) throws Exception {
            int exitCode = ToolRunner.run(new OeDriver(), args);
            System.exit(exitCode);
          }
        }