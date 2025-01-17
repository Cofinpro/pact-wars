# -*- encoding: utf-8 -*-
# stub: rack-proxy 0.7.2 ruby lib

Gem::Specification.new do |s|
  s.name = "rack-proxy"
  s.version = "0.7.2"

  s.required_rubygems_version = Gem::Requirement.new(">= 0") if s.respond_to? :required_rubygems_version=
  s.require_paths = ["lib"]
  s.authors = ["Jacek Becela"]
  s.date = "2022-01-05"
  s.description = "A Rack app that provides request/response rewriting proxy capabilities with streaming."
  s.email = ["jacek.becela@gmail.com"]
  s.homepage = "https://github.com/ncr/rack-proxy"
  s.licenses = ["MIT"]
  s.rubygems_version = "2.4.5.5"
  s.summary = "A request/response rewriting HTTP proxy. A Rack app."

  s.installed_by_version = "2.4.5.5" if s.respond_to? :installed_by_version

  if s.respond_to? :specification_version then
    s.specification_version = 4

    if Gem::Version.new(Gem::VERSION) >= Gem::Version.new('1.2.0') then
      s.add_runtime_dependency(%q<rack>, [">= 0"])
      s.add_development_dependency(%q<rack-test>, [">= 0"])
      s.add_development_dependency(%q<test-unit>, [">= 0"])
    else
      s.add_dependency(%q<rack>, [">= 0"])
      s.add_dependency(%q<rack-test>, [">= 0"])
      s.add_dependency(%q<test-unit>, [">= 0"])
    end
  else
    s.add_dependency(%q<rack>, [">= 0"])
    s.add_dependency(%q<rack-test>, [">= 0"])
    s.add_dependency(%q<test-unit>, [">= 0"])
  end
end
