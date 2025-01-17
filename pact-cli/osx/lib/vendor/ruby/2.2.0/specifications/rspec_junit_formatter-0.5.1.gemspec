# -*- encoding: utf-8 -*-
# stub: rspec_junit_formatter 0.5.1 ruby lib

Gem::Specification.new do |s|
  s.name = "rspec_junit_formatter"
  s.version = "0.5.1"

  s.required_rubygems_version = Gem::Requirement.new(">= 2.0.0") if s.respond_to? :required_rubygems_version=
  s.metadata = { "changelog_uri" => "https://github.com/sj26/rspec_junit_formatter/blob/HEAD/CHANGELOG.md" } if s.respond_to? :metadata=
  s.require_paths = ["lib"]
  s.authors = ["Samuel Cochran"]
  s.cert_chain = ["-----BEGIN CERTIFICATE-----\nMIIDKDCCAhCgAwIBAgIBCDANBgkqhkiG9w0BAQsFADA6MQ0wCwYDVQQDDARzajI2\nMRQwEgYKCZImiZPyLGQBGRYEc2oyNjETMBEGCgmSJomT8ixkARkWA2NvbTAeFw0y\nMTA0MjcwMzIxMjZaFw0yMjA0MjcwMzIxMjZaMDoxDTALBgNVBAMMBHNqMjYxFDAS\nBgoJkiaJk/IsZAEZFgRzajI2MRMwEQYKCZImiZPyLGQBGRYDY29tMIIBIjANBgkq\nhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsr60Eo/ttCk8GMTMFiPr3GoYMIMFvLak\nxSmTk9YGCB6UiEePB4THSSA5w6IPyeaCF/nWkDp3/BAam0eZMWG1IzYQB23TqIM0\n1xzcNRvFsn0aQoQ00k+sj+G83j3T5OOV5OZIlu8xAChMkQmiPd1NXc6uFv+Iacz7\nkj+CMsI9YUFdNoU09QY0b+u+Rb6wDYdpyvN60YC30h0h1MeYbvYZJx/iZK4XY5zu\n4O/FL2ChjL2CPCpLZW55ShYyrzphWJwLOJe+FJ/ZBl6YXwrzQM9HKnt4titSNvyU\nKzE3L63A3PZvExzLrN9u09kuWLLJfXB2sGOlw3n9t72rJiuBr3/OQQIDAQABozkw\nNzAJBgNVHRMEAjAAMAsGA1UdDwQEAwIEsDAdBgNVHQ4EFgQU99dfRjEKFyczTeIz\nm3ZsDWrNC80wDQYJKoZIhvcNAQELBQADggEBAInkmTwBeGEJ7Xu9jjZIuFaE197m\nYfvrzVoE6Q1DlWXpgyhhxbPIKg2acvM/Z18A7kQrF7paYl64Ti84dC64seOFIBNx\nQj/lxzPHMBoAYqeXYJhnYIXnvGCZ4Fkic5Bhs+VdcDP/uwYp3adqy+4bT/XDFZQg\ntSjrAOTg3wck5aI+Tz90ONQJ83bnCRr1UPQ0T3PbWMjnNsEa9CAxUB845Sg+9yUz\nTvf+pbX8JT9rawFDogxPhL7eRAbjg4MH9amp5l8HTVCAsW8vqv7wM4rtMNAaXmik\nLJghfDEf70fTtbs4Zv57pPhn1b7wBNf8fh+TZOlYAA6dFtQXoCwfE6bWgQU=\n-----END CERTIFICATE-----\n"]
  s.date = "2022-01-06"
  s.description = "RSpec results that your continuous integration service can read."
  s.email = "sj26@sj26.com"
  s.homepage = "https://github.com/sj26/rspec_junit_formatter"
  s.licenses = ["MIT"]
  s.required_ruby_version = Gem::Requirement.new(">= 2.0.0")
  s.rubygems_version = "2.4.5.5"
  s.summary = "RSpec JUnit XML formatter"

  s.installed_by_version = "2.4.5.5" if s.respond_to? :installed_by_version

  if s.respond_to? :specification_version then
    s.specification_version = 4

    if Gem::Version.new(Gem::VERSION) >= Gem::Version.new('1.2.0') then
      s.add_runtime_dependency(%q<rspec-core>, ["!= 2.12.0", "< 4", ">= 2"])
      s.add_development_dependency(%q<bundler>, [">= 0"])
      s.add_development_dependency(%q<appraisal>, [">= 0"])
      s.add_development_dependency(%q<nokogiri>, [">= 1.8.2", "~> 1.8"])
      s.add_development_dependency(%q<rake>, [">= 0"])
      s.add_development_dependency(%q<coderay>, [">= 0"])
    else
      s.add_dependency(%q<rspec-core>, ["!= 2.12.0", "< 4", ">= 2"])
      s.add_dependency(%q<bundler>, [">= 0"])
      s.add_dependency(%q<appraisal>, [">= 0"])
      s.add_dependency(%q<nokogiri>, [">= 1.8.2", "~> 1.8"])
      s.add_dependency(%q<rake>, [">= 0"])
      s.add_dependency(%q<coderay>, [">= 0"])
    end
  else
    s.add_dependency(%q<rspec-core>, ["!= 2.12.0", "< 4", ">= 2"])
    s.add_dependency(%q<bundler>, [">= 0"])
    s.add_dependency(%q<appraisal>, [">= 0"])
    s.add_dependency(%q<nokogiri>, [">= 1.8.2", "~> 1.8"])
    s.add_dependency(%q<rake>, [">= 0"])
    s.add_dependency(%q<coderay>, [">= 0"])
  end
end
