# -*- encoding: utf-8 -*-
# stub: thor 1.2.1 ruby lib

Gem::Specification.new do |s|
  s.name = "thor"
  s.version = "1.2.1"

  s.required_rubygems_version = Gem::Requirement.new(">= 1.3.5") if s.respond_to? :required_rubygems_version=
  s.metadata = { "bug_tracker_uri" => "https://github.com/rails/thor/issues", "changelog_uri" => "https://github.com/rails/thor/releases/tag/v1.2.1", "documentation_uri" => "http://whatisthor.com/", "rubygems_mfa_required" => "true", "source_code_uri" => "https://github.com/rails/thor/tree/v1.2.1", "wiki_uri" => "https://github.com/rails/thor/wiki" } if s.respond_to? :metadata=
  s.require_paths = ["lib"]
  s.authors = ["Yehuda Katz", "Jos\u{e9} Valim"]
  s.date = "2022-01-04"
  s.description = "Thor is a toolkit for building powerful command-line interfaces."
  s.email = "ruby-thor@googlegroups.com"
  s.executables = ["thor"]
  s.files = ["bin/thor"]
  s.homepage = "http://whatisthor.com/"
  s.licenses = ["MIT"]
  s.required_ruby_version = Gem::Requirement.new(">= 2.0.0")
  s.rubygems_version = "2.4.5.5"
  s.summary = "Thor is a toolkit for building powerful command-line interfaces."

  s.installed_by_version = "2.4.5.5" if s.respond_to? :installed_by_version

  if s.respond_to? :specification_version then
    s.specification_version = 4

    if Gem::Version.new(Gem::VERSION) >= Gem::Version.new('1.2.0') then
      s.add_development_dependency(%q<bundler>, ["< 3", ">= 1.0"])
    else
      s.add_dependency(%q<bundler>, ["< 3", ">= 1.0"])
    end
  else
    s.add_dependency(%q<bundler>, ["< 3", ">= 1.0"])
  end
end
