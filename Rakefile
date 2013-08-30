
# Copyright 2013 Urban Airship

desc "Build the docs for the API Client using javasphinx. This produces rst for sphinx"
task :build_javadocs do
  puts "Building javadocs"
  # These need to be broken up into per directory calls to control
  # what gets into the docs
  sh "javasphinx-apidoc -f -o ./docs src/main/java/com/urbanairship/api/client/"
  sh "javasphinx-apidoc -f -o ./docs src/main/java/com/urbanairship/api/push/model/audience"
  sh "javasphinx-apidoc -f -o ./docs src/main/java/com/urbanairship/api/push/model/notification/ios"
  puts "Finished build_javadocs"
end


desc "Build the Sphinx docs. This builds the actual HTML"
task :build_sphinx_docs do
  puts "Building Sphinx docs"
  # Change directory for the do block only
  Dir.chdir("./docs") do
    sh "make html"
  end 
  puts "Finished build_sphinx_docs"
end


desc "Glob the javasphinx-apidoc document output directory and append the output to the packages.rst file"
task :setup_packages_rst do
  puts "Setting up packages.rst with package-index.rst files globbed from docs/com/**"
  packages_rst_path = "./docs/packages.rst"
  dir_glob = Dir.glob("docs/com/**/package-index.rst")

  lines = nil
  File.open(packages_rst_path) do |f|
    lines = f.readlines
  end
  
  # Strip out old directories in packages.rst data and append new
  # dirs. 
  updated_lines = lines.select {| line | !line.include? "com/urbanairship"}
  package_index_paths = dir_glob.map do |path|
    truncated_path = path.slice /com\/.*/ 
    # add Tab spaces and  newline for file output
    "  " + truncated_path + "\n"
  end

  tmp_write_path = packages_rst_path + "-tmp"
  File.open(tmp_write_path, 'w') do |fh|
    (updated_lines + package_index_paths).each do |line|
      fh.write(line)
    end
  end
 
  # if the above process didn't exception, move tmp to actual
  FileUtils.mv(tmp_write_path, packages_rst_path, {:verbose => true})

  puts "Finished setup_packages_rst"
end

desc "Clean the docs/com directory, this is not cleaned by javasphinx"
task :clean_docs do
  puts "Cleaning docs"
  if File.directory? "./docs/com"
    FileUtils.remove_entry_secure("./docs/com/")
    puts "Docs cleaned"
  else
    puts "No 'docs/com/' directory, cleaning skipped"
  end
end

# TODO The dependencies for all the tasks need to be setup more cleanly
desc "Build all the docs"
task :build_docs => [:clean_docs, :build_javadocs, :setup_packages_rst, :build_sphinx_docs] do 
   puts "Built the docs"
end

